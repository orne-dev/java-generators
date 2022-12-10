package dev.orne.test.rnd.params;

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

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import dev.orne.test.rnd.AbstractTypedGenerator;

/**
 * Abstract generator that calls an executable with generated parameters to
 * generate values of the target type.
 * 
 * @author <a href="mailto:wamphiry@orne.dev">(w) Iker Hernaez</a>
 * @version 1.0, 2022-12
 * @param <T> The type of generated values
 * @since 0.1
 */
@API(status=Status.EXPERIMENTAL, since="0.1")
public abstract class ExecutableGenerator<T>
extends AbstractTypedGenerator<T> {

    /** The executable to call. */
    private final @NotNull Executable executable;
    /** The generator for the executable parameters. */
    private final @NotNull TargetedGenerator<?>[] parameterGenerators;

    /**
     * Creates a new instance.
     * 
     * @param type The type of generated values
     * @param executable The executable to call
     * @param paramGenerators The generator for the executable parameters
     */
    protected ExecutableGenerator(
            final @NotNull Class<T> type,
            final @NotNull Executable executable,
            final @NotNull TargetedGenerator<?>[] paramGenerators) {
        super(type);
        this.executable = Validate.notNull(executable);
        this.parameterGenerators = Validate.notNull(paramGenerators);
    }

    /**
     * Creates a new instance that will call the specified constructor.
     * 
     * @param <T> The type of the generated instance
     * @param constructor The constructor to call
     * @return The created generator
     */
    public static <T> ConstructorGenerator<T> of(
            final @NotNull Constructor<T> constructor) {
        return ConstructorGenerator.of(constructor);
    }

    /**
     * Creates a new instance that will call the specified factory method.
     * 
     * @param <T> The type of generated values
     * @param type The type of generated values
     * @param method The factory method to call
     * @return The created generator
     */
    public static <T> FactoryMethodGenerator<T> of(
            final @NotNull Class<T> type,
            final @NotNull Method method) {
        return FactoryMethodGenerator.of(type, method);
    }

    /**
     * Returns the executable to call.
     * 
     * @return The executable to call
     */
    public @NotNull Executable getExecutable() {
        return this.executable;
    }

    /**
     * Returns the generator for the executable parameters.
     * 
     * @return The generator for the executable parameters
     */
    @SuppressWarnings("java:S1452")
    public @NotNull TargetedGenerator<?>[] getParameterGenerators() {
        return this.parameterGenerators;
    }

    /**
     * Returns the default instance of the target type.
     * 
     * @return The default instance
     */
    public @NotNull T defaultValue() {
        final Object[] params = new Object[parameterGenerators.length];
        for (int i = 0; i < params.length; i++) {
            final TargetedGenerator<?> generator = parameterGenerators[i];
            params[i] = generator.nullableDefaultValue(Default.class);
        }
        return generate(params);
    }

    /**
     * Returns a random instance of the target type.
     * 
     * @return A random instance
     */
    public @NotNull T randomValue() {
        final Object[] params = new Object[parameterGenerators.length];
        for (int i = 0; i < params.length; i++) {
            final TargetedGenerator<?> generator = parameterGenerators[i];
            params[i] = generator.nullableRandomValue(Default.class);
        }
        return generate(params);
    }

    /**
     * Calls the executable to create a new instance of the target type.
     * 
     * @param params The parameters to use when calling the executable
     * @return The created instance
     */
    protected abstract @NotNull T generate(
            Object[] params);

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(this.executable)
                .append(this.parameterGenerators)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }
        final ExecutableGenerator<?> other = (ExecutableGenerator<?>) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.executable, other.executable)
                .append(this.parameterGenerators, other.parameterGenerators)
                .build();
    }
}
