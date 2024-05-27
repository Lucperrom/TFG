INSERT INTO authorities(id,authority) VALUES (1,'ADMIN')
INSERT INTO authorities(id,authority) VALUES (2,'USER')

INSERT INTO users(id,first_name,last_name,username,email,password,authority) VALUES (1,'admin','admin','admin','admin@admin.com','$2a$12$0YF2Y27tOHyNWjBLse.lR.huVfIavOX2yPh5v6SEu4FdJB92CK5ke',1)
INSERT INTO users(id,first_name,last_name,username,email,password,authority) VALUES (2,'Pedro','Pino','user','pedro@gmail.com','$2a$12$9x3hBRJuJZalMw8HnvJ7OuqRj3ZGI1NlrLSpN9v4qatz1bNq1rxw2',2)

INSERT INTO types(id,name) VALUES (1,'Article')
INSERT INTO types(id,name) VALUES (2,'Book')
INSERT INTO types(id,name) VALUES (3,'Manual')
INSERT INTO types(id,name) VALUES (4,'Thesis')
INSERT INTO types(id,name) VALUES (5,'Technical-report')
INSERT INTO types(id,name) VALUES (6,'Dissertation')
INSERT INTO types(id,name) VALUES (7,'Essay')
INSERT INTO types(id,name) VALUES (8,'Paper')
INSERT INTO types(id,name) VALUES (9,'Book-chapter')
INSERT INTO types(id,name) VALUES (10,'Booklet')
INSERT INTO types(id,name) VALUES (11,'Conference')
INSERT INTO types(id,name) VALUES (12,'Other')

INSERT INTO papers(id,title,authors,publication_year,type_id,abstract_content,notes,keywords,user_id) VALUES(1,'A Comprehensive Study on Machine Learning Algorithms for Predictive Analytics','Pedro P.',2019,1,'This article presents an in-depth analysis of various machine learning algorithms used in predictive analytics. The study compares the performance of algorithms such as decision trees, support vector machines, and neural networks across different datasets to determine their efficacy in real-world applications.', 'This research was supported by the National Science Foundation under grant number XYZ123', 'Machine Learning, Predictive Analytics, Decision Trees, Neural Networks, Support Vector Machines',2)
INSERT INTO papers(id,title,authors,publication_year,type_id,abstract_content,notes,keywords,user_id) VALUES(2,'The Role of CRISPR-Cas9 in Gene Editing: Advances and Ethical Considerations','Paco O., Pedro P.',2022,4,'This thesis explores the advancements in CRISPR-Cas9 gene editing technology and its implications for biomedical research. It also discusses the ethical considerations associated with gene editing, particularly in human embryos, and proposes guidelines for responsible use.', 'Ethical implications discussed are based on the latest guidelines from the International Society for Stem Cell Research (ISSCR)', 'CRISPR-Cas9, Gene Editing, Biomedical Research, Ethics, Genetic Engineering',2)


INSERT INTO company(id,name,description,phone,email,support_phone,support_email) VALUES(1,'PUBUS S.L.','Publication Management Company',928647327,'company@pubus.com',926541873,'support@pubus.com')