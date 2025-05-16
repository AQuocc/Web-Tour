-- Drop existing enum fields if they exist
ALTER TABLE bookings DROP COLUMN IF EXISTS payment_status;
ALTER TABLE bookings DROP COLUMN IF EXISTS payment_method;
ALTER TABLE bookings DROP COLUMN IF EXISTS transaction_id;
ALTER TABLE bookings DROP COLUMN IF EXISTS payment_date;

-- Add payment fields with proper types
ALTER TABLE bookings ADD COLUMN payment_status VARCHAR(20) DEFAULT 'PENDING' NOT NULL;
ALTER TABLE bookings ADD COLUMN payment_method VARCHAR(20);
ALTER TABLE bookings ADD COLUMN transaction_id VARCHAR(100);
ALTER TABLE bookings ADD COLUMN payment_date DATETIME;

-- Update existing bookings to have PENDING payment status
UPDATE bookings SET payment_status = 'PENDING' WHERE payment_status IS NULL;