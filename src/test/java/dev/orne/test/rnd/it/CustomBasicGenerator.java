package dev.orne.test.rnd.it;

import java.time.OffsetDateTime;

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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import dev.orne.test.rnd.AbstractGenerator;
import dev.orne.test.rnd.Generators;
import dev.orne.test.rnd.Priority;
import dev.orne.test.rnd.generators.EnumGenerator;

/**
 * Custom generator for an enumeration type that prevents generation of an
 * undesired value.
 * <p>
 * Registered through {@code META-INF/services/dev.orne.test.rnd.Generator}
 * SPI.
 * <p>
 * Generator has no {@code Priority} annotation, so gets the default priority
 * ({@value dev.orne.test.rnd.Priority#DEFAULT}), which is greater than built in
 * enumeration values generator
 * ({@value dev.orne.test.rnd.Priority#GENERIC_GENERATORS}) and therefore
 * takes precedence for generation of the supported types.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-10
 * @since 0.1
 * @see Priority
 * @see EnumGenerator
 */
public class CustomBasicGenerator
extends AbstractGenerator {

    /**
     * Creates a new instance.
     */
    public CustomBasicGenerator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(@NotNull Class<?> type) {
        return BasicInterface.class.equals(type)
                || BasicImpl.class.equals(type)
                || ExtendedImpl.class.equals(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T defaultValue(@NotNull Class<T> type) {
        assertSupported(type);
        final BasicImpl result;
        if (ExtendedImpl.class.equals(type)) {
            result = new ExtendedImpl();
        } else {
            result = new BasicImpl();
        }
        return type.cast(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @NotNull T randomValue(@NotNull Class<T> type) {
        assertSupported(type);
        final BasicImpl result;
        if (ExtendedImpl.class.equals(type)) {
            final ExtendedImpl bean = new ExtendedImpl();
            bean.setCreationDate(Generators.randomValue(OffsetDateTime.class));
            bean.setUpdateDate(Generators.randomValue(OffsetDateTime.class));
            result = bean;
        } else {
            result = new BasicImpl();
        }
        result.setCode(RandomUtils.nextInt(1, Integer.MAX_VALUE));
        result.setName(Generators.randomValue(String.class));
        return type.cast(result);
    }

    /**
     * Test interface.
     */
    public interface BasicInterface {
        /**
         * Returns the code.
         * @return The code.
         */
        int getCode();
        /**
         * Returns the name.
         * @return The name.
         */
        String getName();
    }

    /**
     * Test base bean.
     */
    public static class BasicImpl
    implements BasicInterface {
        /** The code. */
        private int code;
        /** The name. */
        private String name;
        /**
         * Creates a new instance.
         */
        public BasicImpl() {
            super();
        }
        public int getCode() {
            return code;
        }
        /**
         * Sets the code.
         * @param code The code.
         */
        public void setCode(int code) {
            this.code = code;
        }
        public String getName() {
            return name;
        }
        /**
         * Sets the name.
         * @param name The name.
         */
        public void setName(String name) {
            this.name = name;
        }
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }
    }

    /**
     * Test extended bean.
     */
    public static class ExtendedImpl
    extends BasicImpl {
        /** The creation date. */
        private OffsetDateTime creationDate;
        /** The update date. */
        private OffsetDateTime updateDate;
        /**
         * Creates a new instance.
         */
        public ExtendedImpl() {
            super();
        }
        /**
         * Returns the creation date.
         * @return The creation date.
         */
        public OffsetDateTime getCreationDate() {
            return creationDate;
        }
        /**
         * Sets the creation date.
         * @param creationDate The creation date.
         */
        public void setCreationDate(OffsetDateTime creationDate) {
            this.creationDate = creationDate;
        }
        /**
         * Returns the update date.
         * @return The update date.
         */
        public OffsetDateTime getUpdateDate() {
            return updateDate;
        }
        /**
         * Sets the update date.
         * @param updateDate The update date.
         */
        public void setUpdateDate(OffsetDateTime updateDate) {
            this.updateDate = updateDate;
        }
    }
}
