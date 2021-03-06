/*
 * Hex - a hex viewer and annotator
 * Copyright (C) 2009  Trejkaz, Hex Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.trypticon.hex.gui.notebook;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import org.trypticon.hex.anno.AnnotationCollection;
import org.trypticon.hex.anno.AnnotationCollectionListener;
import org.trypticon.hex.anno.MemoryAnnotationCollection;
import org.trypticon.hex.anno.AnnotationCollectionEvent;
import org.trypticon.hex.binary.Binary;
import org.trypticon.hex.binary.BinaryFactory;

/**
 * Holds a set of annotations along with a reference to the file the user is working on.
 *
 * TODO: I want a better name for this class.  It's a document, but the user won't see
 *       it as a document.  What do you call a collection of annotations?
 *
 * @author trejkaz
 */
public class DefaultNotebook implements Notebook
{
    private static final Logger logger = Logger.getLogger(DefaultNotebook.class.getName());
    private PropertyChangeSupport propertyChanges;
    private URL notebookLocation;
    private String name;
    private final URL binaryLocation;
    private final AnnotationCollection annotations;
    private Binary binary;

    private final Object openLock = new Object();

    /**
     * Dirty flag.  Set to {@code true} while the notebook has unsaved changes.
     */
    private boolean dirty;

    /**
     * Constructs a new unsaved notebook with a default in-memory annotation collection.
     *
     * @param binaryLocation the location of the binary.
     */
    public DefaultNotebook(URL binaryLocation) {
        this(binaryLocation, new MemoryAnnotationCollection());
    }

    /**
     * Constructs a notebook with an existing annotation collection.
     *
     * @param binaryLocation the location of the bianry.
     * @param annotations the annotation collection.
     */
    public DefaultNotebook(URL binaryLocation, AnnotationCollection annotations) {
        this.binaryLocation = binaryLocation;
        this.annotations = annotations;

        // New annotations appearing mean we need to be saved.
        annotations.addAnnotationCollectionListener(new AnnotationCollectionListener() {
            public void annotationsChanged(AnnotationCollectionEvent event) {
                setDirty(true);
            }
        });

        String path = binaryLocation.getPath();
        int lastSlash = path.lastIndexOf('/');
        String baseName = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;

        this.name = "New: " + baseName;

        // Presumed dirty until someone sets the location.
        setDirty(true);
    }

    /**
     * <p>Opens the notebook.  Ultimately this means opening the binary.</p>
     *
     * <p>This is distinct from the constructor as this class can be thought to
     *    be serialisable, even though we are not performing the serialisation
     *    using the normal {@code Serializable} API.</p>
     *
     * @throws IOException if an I/O error prevents opening the binary.
     */
    public void open() throws IOException {

        // TODO: Support relative path to binary file.

        synchronized (openLock) {
            if (binary != null) {
                logger.warning("Already open when open() was called, doing nothing.");
            } else {
                binary = BinaryFactory.open(binaryLocation);
            }
        }
    }

    /**
     * Closes the notebook.
     */
    public void close() {
        synchronized (openLock) {
            if (binary == null) {
                logger.warning("Already closed when close() was called, doing nothing.");
            } else {
                binary.close();
                binary = null;
            }
        }
    }

    /**
     * Gets the location from which the notebook was opened, or to which it was last saved.
     *
     * @return the notebook location.
     */
    public URL getNotebookLocation() {
        return notebookLocation;
    }

    /**
     * <p>Sets the location of the notebook.</p>
     *
     * <p>Also indicates that the notebook was just saved to that location, thus
     *    sets the dirty flag to {@code false}.</p>
     *
     * @param notebookLocation the new notebook location.
     * @see #getNotebookLocation()
     */
    public void setNotebookLocation(URL notebookLocation) {
        if (notebookLocation == null) {
            throw new IllegalArgumentException("notebook location cannot be null");
        }

        if (!notebookLocation.equals(this.notebookLocation)) {
            this.notebookLocation = notebookLocation;

            String path = notebookLocation.getPath();
            int lastSlash = path.lastIndexOf('/');
            String lastPathComponent = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
            int dot = lastPathComponent.indexOf('.');
            setName(dot >= 0 ? lastPathComponent.substring(0, dot) : lastPathComponent);
        }

        setDirty(false);
    }

    public URL getBinaryLocation() {
        return binaryLocation;
    }

    public AnnotationCollection getAnnotations() {
        return annotations;
    }

    public Binary getBinary() {
        return binary;
    }

    /**
     * Tests if the notebook is open.
     *
     * @return {@code true} if it is open, {@code false} if it is closed.
     */
    public boolean isOpen() {
        return binary != null;
    }

    /**
     * Gets the name of the notebook.  Currently this is derived from the location
     * of the notebook but it might become custom metadata later.
     *
     * This is a bound JavaBeans property.
     *
     * @return the name of the notebook.
     */
    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (!name.equals(this.name)) {
            String oldName = this.name;
            this.name = name;
            firePropertyChange("name", oldName, name);
            setDirty(true);
        }
    }

    /**
     * Tests if the notebook in this pane has unsaved changes.
     *
     * @return {@code true} if the notebook has unsaved changes.
     */
    public boolean isDirty() {
        return dirty;
    }

    private void setDirty(boolean dirty) {
        if (this.dirty != dirty) {
            boolean oldDirty = this.dirty;
            this.dirty = dirty;
            firePropertyChange("dirty", oldDirty, dirty);
        }
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if (propertyChanges == null) {
            propertyChanges = new PropertyChangeSupport(this);
        }
        propertyChanges.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if (propertyChanges != null) {
            propertyChanges.removePropertyChangeListener(propertyName, listener);
        }
    }

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (propertyChanges != null) {
            propertyChanges.firePropertyChange(propertyName, oldValue, newValue);
        }
    }
}
