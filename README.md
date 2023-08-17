# Taboola-Take-Home-Test
Taboola RnD Backend Engineer Intern Take Home Test

## Problem 1: JSON Parser

This file contains coding solution from problem#1 from the test. It takes input file where filepath needs to be updated to change test case/input string. Just for relevance to the question initially JSON file is used as input in "input.json". This can also be changed as .txt file. Bottom line it takes input string from the file in the form of string data type.


## Problem 2: Serialize and Deserialize Binary tree

This file contains three java file - code to serialize and deserialize tree, code to create tree node (Node.java) and interface file for Tree Serializer. Tree Serialzier main file (CyclicTreeDetectionAndSerializer.java) takes into consideration all mentioned assumption and fulfills all requirements. It can also work in cyclic tree which is demanded as bonus question. If you desire to throw RunTime Exception, then this is also convered just by enabling a line of code.

### Problem 2.3: Suggest changes that should be done to support any data type

A. In order to handle Generic datatypes in Tree, the first change that would be needed would be to make the data type of 'num' variable to Generic. This will allow an instance of Node class to have different data types as values.

B. While serializing, initially all the numbers were being converted to String, but now with Nodes having different datatypes, a different serializing format will be required. For instance, '|' can be used as a delimiter to separate different nodes in the serialized string. ';' can be used to separate the value and the data type.

For example.

'1';'int' | 'Shrey';'string' | 'true';'bool' | 'true';'string'

These 4 nodes are 1(int), Shrey(string), true(bool), and true(string) respectively.
This will help the code to recognize the data type and assign the value to the Node while recreating the original tree.
Moreover, to make the code more modular, for each data type, there can be a separate toString function that will return the above string format, which can further be append in the original string

C. Similar to a toString functon there would be a toNode function that will take in value and type, both in string, and then based on the type, it will create Nodes with respective data type values.

## Problem 3: 

A. 

create table product (id int AUTO_INCREMENT not null, name varchar(250), category varchar(250), primary key(id))


B.

create table product_price (id int AUTO_INCREMENT not null, product_id int not null, price int, primary key(id), FOREIGN KEY (product_id) REFERENCES product(id))


C. 

CREATE TABLE product_price_change_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    old_price INT NOT NULL,
    new_price INT NOT NULL,
    changed_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    changed_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);



### a query to join “product” table and “product price” table together to show the product name, category, price, and who/when it gets updated.
SELECT 
    p.name AS product_name, 
    p.category AS category, 
    pp.price AS price,
    pl.changed_by AS updated_by,
    pl.changed_date AS updated_time
FROM 
    product p INNER JOIN product_price pp 
ON 
    p.id = pp.product_id
INNER JOIN product_price_change_log pl
ON
	pl.product_id = p.id
