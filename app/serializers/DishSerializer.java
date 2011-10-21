/*
 * Copyright (c) 2011 Zauber S.A.  -- All rights reserved
 */
package serializers;

import models.Dish;
import flexjson.JSONSerializer;

/**
 * Serializer for {@link Dish}
 *
 * @author Martin Silva
 * @since Oct 21, 2011
 */
public class DishSerializer implements Serializer<Dish>{

    /**
     * Creates the DishSerializer.
     *
     */
    private final JSONSerializer serializer;

    public DishSerializer() {
         this.serializer =
            new JSONSerializer().include(
                "id",
                "price",
                "description"
            )
            .exclude("*");
    }

    /** @see serializers.Serializer#serialize(models.Dish) */
    @Override
    public String serialize(final Dish dish) {
        return this.serializer.serialize(dish);
    }

}
