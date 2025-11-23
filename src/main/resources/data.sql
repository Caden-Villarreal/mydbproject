-- =====================
-- CUSTOMER
-- =====================
INSERT INTO customer (first_name, last_name, email, phone)
VALUES
('Alice', 'Johnson', 'alice@example.com', '555-1111'),
('Bob', 'Smith', 'bob@example.com', '555-2222'),
('Carol', 'Martinez', 'carol@example.com', '555-3333'),
('David', 'Nguyen', 'david@example.com', '555-4444'),
('Eve', 'Lopez', 'eve@example.com', '555-5555');

-- =====================
-- CUSTOMER ADDRESS REF
-- =====================
INSERT INTO customer_address_ref (address_type, street, city, state, zip_code, customer_id)
VALUES
('Billing', '123 Main St', 'Omaha', 'NE', '68102', 1),
('Shipping', '456 Oak St', 'Omaha', 'NE', '68104', 1),
('Billing', '789 Pine St', 'Lincoln', 'NE', '68508', 2),
('Shipping', '101 Maple St', 'Omaha', 'NE', '68105', 3),
('Billing', '202 Elm St', 'Omaha', 'NE', '68107', 4);

-- =====================
-- LENS
-- =====================
INSERT INTO lens (lens_type, brand, price)
VALUES
('Single Vision', 'RayOptix', 89.99),
('Bifocal', 'ClearView', 119.99),
('Progressive', 'SharpFocus', 139.50),
('Blue Light Filter', 'ZenLens', 99.99),
('Reading', 'LensPro', 59.95);

-- =====================
-- INVENTORY
-- =====================
INSERT INTO inventory (stock_quantity, warehouse_location, lens_id)
VALUES
(200, 'Warehouse A', 1),
(150, 'Warehouse B', 2),
(120, 'Warehouse A', 3),
(300, 'Warehouse C', 4),
(80, 'Warehouse D', 5);

-- =====================
-- ORDERS
-- =====================
INSERT INTO orders (order_date, total_amount, customer_id)
VALUES
('2025-10-01', 259.97, 1),
('2025-10-02', 139.50, 2),
('2025-10-03', 299.48, 3),
('2025-10-04', 179.98, 4),
('2025-10-05', 89.99, 5);

-- =====================
-- ORDER LINE ITEMS
-- =====================
INSERT INTO order_line_item (quantity, price, order_id, lens_id)
VALUES
(2, 89.99, 1, 1),
(1, 79.99, 1, 4),
(1, 139.50, 2, 3),
(3, 99.99, 3, 4),
(1, 59.95, 4, 5);

-- =====================
-- SHIPPING INFO
-- =====================
INSERT INTO shipping_info (carrier, tracking_number, ship_date, status, order_id)
VALUES
('UPS', '1Z999AA101', '2025-10-02', 'Delivered', 1),
('FedEx', '777FX202', '2025-10-03', 'In Transit', 2),
('USPS', '9400111899', '2025-10-04', 'Delivered', 3),
('DHL', 'DHL123456', '2025-10-05', 'Processing', 4),
('UPS', '1Z888BB303', '2025-10-06', 'Label Created', 5);