-- 1. 工装类别表
CREATE TABLE IF NOT EXISTS Tool_Category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- 2. 工厂表
CREATE TABLE IF NOT EXISTS Factory (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

-- 3. 产线表
CREATE TABLE IF NOT EXISTS Production_Line (
    id SERIAL PRIMARY KEY,
    factory_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY (factory_id) REFERENCES Factory(id) ON DELETE CASCADE
);

-- 4. 工序表
CREATE TABLE IF NOT EXISTS Process (
    id SERIAL PRIMARY KEY,
    production_line_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY (production_line_id) REFERENCES Production_Line(id) ON DELETE CASCADE
);

-- 5. 工装类别-产线-工序 关联表
CREATE TABLE IF NOT EXISTS Tool_Category_Production (
    id SERIAL PRIMARY KEY,
    tool_category_id INT NOT NULL,
    factory_id INT NOT NULL,
    production_line_id INT NOT NULL,
    process_id INT NOT NULL,
    model VARCHAR(255),
    drawing VARCHAR(255),
    FOREIGN KEY (tool_category_id) REFERENCES Tool_Category(id) ON DELETE CASCADE,
    FOREIGN KEY (factory_id) REFERENCES Factory(id) ON DELETE CASCADE,
    FOREIGN KEY (production_line_id) REFERENCES Production_Line(id) ON DELETE CASCADE,
    FOREIGN KEY (process_id) REFERENCES Process(id) ON DELETE CASCADE
);

-- 6. 工装实例表
CREATE TABLE IF NOT EXISTS Tool_Instance (
    id SERIAL PRIMARY KEY,
    tool_category_production_id INT NOT NULL,
    instance_code VARCHAR(255) UNIQUE NOT NULL,
    manufacture_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'active',
    FOREIGN KEY (tool_category_production_id) REFERENCES Tool_Category_Production(id) ON DELETE CASCADE
);

-- 7. 文件类型表
CREATE TABLE IF NOT EXISTS File_Type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- 8. 用户表
CREATE TABLE IF NOT EXISTS Users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 9. 附件表
CREATE TABLE IF NOT EXISTS Attachment (
    id SERIAL PRIMARY KEY,
    file_type_id INT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    uploader_id INT NOT NULL,
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT DEFAULT 1,
    is_deprecated BOOLEAN DEFAULT FALSE,
    parent_attachment_id INT DEFAULT NULL,
    FOREIGN KEY (file_type_id) REFERENCES File_Type(id) ON DELETE CASCADE,
    FOREIGN KEY (uploader_id) REFERENCES Users(id) ON DELETE SET NULL,
    FOREIGN KEY (parent_attachment_id) REFERENCES Attachment(id) ON DELETE SET NULL
);

-- 10. 附件-工装类别-产线-工序 关联表
CREATE TABLE IF NOT EXISTS Attachment_Assignment (
    id SERIAL PRIMARY KEY,
    attachment_id INT NOT NULL,
    tool_category_production_id INT NOT NULL,
    assigned_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (attachment_id) REFERENCES Attachment(id) ON DELETE CASCADE,
    FOREIGN KEY (tool_category_production_id) REFERENCES Tool_Category_Production(id) ON DELETE CASCADE
);

-- 11. 附件历史记录表
CREATE TABLE IF NOT EXISTS Attachment_History (
    id SERIAL PRIMARY KEY,
    attachment_id INT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    uploader_id INT NOT NULL,
    upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT NOT NULL,
    is_deprecated BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (attachment_id) REFERENCES Attachment(id) ON DELETE CASCADE,
    FOREIGN KEY (uploader_id) REFERENCES Users(id) ON DELETE SET NULL
);
