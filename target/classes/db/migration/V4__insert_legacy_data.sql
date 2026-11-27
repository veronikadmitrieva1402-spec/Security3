INSERT INTO security_requests (id, object_address, status, service_type_id, created_at)
SELECT gen_random_uuid(), 'ул. Ленина, д. 10', 'NEW', (SELECT id FROM service_types WHERE name = 'Физическая охрана' LIMIT 1), CURRENT_TIMESTAMP;

INSERT INTO security_requests (id, object_address, status, service_type_id, created_at)
SELECT gen_random_uuid(), 'ул. Пушкина, д. 5', 'IN_PROGRESS', (SELECT id FROM service_types WHERE name = 'Пультовая охрана' LIMIT 1), CURRENT_TIMESTAMP;

INSERT INTO security_requests (id, object_address, status, service_type_id, created_at)
SELECT gen_random_uuid(), 'пр. Мира, д. 22', 'DONE', (SELECT id FROM service_types WHERE name = 'Физическая охрана' LIMIT 1), CURRENT_TIMESTAMP;