delete from logs;
delete from operations;
delete from accounts;
delete from clients;

insert into clients (id, login, pass, email, name, surname, second_name) values
(1, 'alex', '$2a$10$EVE4IwLVT8jw5aH95OTrvu4hcFNFt2fD44ZDdCuz.dxwnPU8.Ltd.', 'alexpit63@gmail.com', 'alex', 'pit', 'qwe'),
(2, 'alex1', '$2a$10$EVE4IwLVT8jw5aH95OTrvu4hcFNFt2fD44ZDdCuz.dxwnPU8.Ltd.', 'aleksandr.pitasov@yandex.ru', 'alex', 'pit', 'qwe');

insert into accounts (id, client_id, currency_id, deposit) values
(1, 1, 1, 0.00),
(2, 2, 1, 0.00);