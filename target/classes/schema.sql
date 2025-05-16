-- Add payment fields if not exist
ALTER TABLE bookings 
    ADD COLUMN IF NOT EXISTS payment_status VARCHAR(20) DEFAULT 'PENDING',
    ADD COLUMN IF NOT EXISTS payment_method VARCHAR(20),
    ADD COLUMN IF NOT EXISTS transaction_id VARCHAR(100),
    ADD COLUMN IF NOT EXISTS payment_date DATETIME;

-- Make payment_status not null after setting defaults
UPDATE bookings SET payment_status = 'PENDING' WHERE payment_status IS NULL;
ALTER TABLE bookings MODIFY payment_status VARCHAR(20) NOT NULL;