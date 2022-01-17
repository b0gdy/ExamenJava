CREATE TABLE payments (

    id INT NOT NULL AUTO_INCREMENT,
    type VARCHAR(100) NOT NULL,
    customer VARCHAR(200) NOT NULL,
    amount DOUBLE NOT NULL,
    status VARCHAR(100) NOT NULL,

    PRIMARY  KEY (id)

);