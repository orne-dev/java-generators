package dev.orne.test.rnd;

/*-
 * #%L
 * Orne Test Generators
 * %%
 * Copyright (C) 2022 Orne Developments
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Comparator;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

/**
 * Annotation for generators priority.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE })
@API(status=Status.STABLE, since="0.1")
public @interface Priority {

    /** The default priority. */
    public static final int DEFAULT = 0;
    /** The minimum priority. */
    public static final int MIN = Integer.MIN_VALUE;
    /** The priority for built in native types generators. */
    public static final int NATIVE_GENERATORS = -1000;
    /** The priority for built in generic types generators. */
    public static final int GENERIC_GENERATORS = -2000;
    /** The maximum priority. */
    public static final int MAX = Integer.MAX_VALUE;

    /**
     * The generator comparator by priority.
     */
    public static final Comparator<Generator> COMPARATOR =
            Comparator.comparingInt(Generator::getPriority).reversed();

    /**
     * Returns the priority of the generator.
     *  
     * @return The priority of the generator
     */
    int value() default DEFAULT;
}
