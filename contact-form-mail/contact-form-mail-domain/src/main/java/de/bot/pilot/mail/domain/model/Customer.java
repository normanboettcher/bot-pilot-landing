package de.bot.pilot.mail.domain.model;

import org.immutables.value.Value;

/**
 * Domain value object representing a contact form submitter.
 * Generated {@code ImmutableCustomer} provides a type-safe builder.
 */
@Value.Immutable
public interface Customer {
    String firstName();
    String lastName();
    String email();
    String company();
}
