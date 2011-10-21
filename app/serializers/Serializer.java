/*
 * Copyright (c) 2011 Zauber S.A. -- All rights reserved
 */
package serializers;


/**
 * TODO Descripcion de la clase. Los comentarios van en castellano.
 *
 *
 * @author Martin Silva
 * @since Oct 21, 2011
 */
public interface Serializer<T> {

    /**
     * Serializes the object
     *
     * @return <code>JSONSerializer</code> with the serializer.
     */
    String serialize(final T t);

}