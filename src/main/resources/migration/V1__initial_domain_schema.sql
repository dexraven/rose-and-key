-- Enables UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Party & Profile Domain
CREATE TABLE client_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_type VARCHAR(32) NOT NULL DEFAULT 'GUEST',
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_e164 VARCHAR(20) NOT NULL,
    vaulted_customer_token VARCHAR(255),
    default_payment_method_token VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX uq_client_registered_email
    ON client_profiles (email)
    WHERE account_type = 'REGISTERED';

CREATE TABLE companions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    primary_client_id UUID NOT NULL REFERENCES client_profiles(id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    relationship VARCHAR(32) NOT NULL DEFAULT 'COMPANION',
    date_of_birth DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_companions_primary_client ON companions(primary_client_id);

-- Intake & Trip Boundary
CREATE TABLE trip_intakes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID NOT NULL REFERENCES client_profiles(id) ON DELETE RESTRICT,
    assigned_agent_id VARCHAR(100) NOT NULL,
    destination_target VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'INTAKE_SUBMITTED',
    earliest_departure DATE NOT NULL,
    latest_return DATE NOT NULL,
    target_budget_cents BIGINT NOT NULL,
    preferred_currency CHAR(3) NOT NULL DEFAULT 'USD',
    party_headcount INT NOT NULL DEFAULT 1,
    special_notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_trip_intakes_client ON trip_intakes(client_id);
CREATE INDEX idx_trip_intakes_agent_status ON trip_intakes(assigned_agent_id, status);

-- Financial & Vendor Ledger Boundary
CREATE TABLE booking_records (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    trip_intake_id UUID NOT NULL REFERENCES trip_intakes(id) ON DELETE RESTRICT,
    supplier_type VARCHAR(32) NOT NULL,
    supplier_confirmation_code VARCHAR(100),
    package_reference_id VARCHAR(100),
    subtotal_cents BIGINT NOT NULL,
    taxes_and_fees_cents BIGINT NOT NULL,
    total_cents BIGINT NOT NULL,
    currency CHAR(3) NOT NULL DEFAULT 'USD',
    confirmed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_booking_records_intake ON booking_records(trip_intake_id);

CREATE TABLE payment_transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    booking_record_id UUID NOT NULL REFERENCES booking_records(id) ON DELETE RESTRICT,
    gateway_transaction_id VARCHAR(255) NOT NULL,
    gateway_payment_method_token VARCHAR(255) NOT NULL,
    amount_cents BIGINT NOT NULL,
    currency CHAR(3) NOT NULL DEFAULT 'USD',
    status VARCHAR(32) NOT NULL DEFAULT 'AUTHORIZED',
    error_message TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_payments_booking ON payment_transactions(booking_record_id);