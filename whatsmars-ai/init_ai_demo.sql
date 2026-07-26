-- brew install postgresql@18
-- brew install pgvector
-- brew services start postgresql@18
-- createdb $USER
-- psql -f init_ai_demo.sql

-- 1. 创建专属用户（如果不存在）
CREATE USER ai_user WITH PASSWORD 'ai_user';

-- 2. 创建专属数据库（如果不存在）
CREATE DATABASE ai_demo OWNER ai_user;

-- 3. 连接到新创建的数据库（在 psql 脚本中可以使用 \connect）
\connect ai_demo;

-- 4. 启用 pgvector 扩展（必须先连到 ai_demo 数据库再启用）
CREATE EXTENSION IF NOT EXISTS vector;

-- 5. 初始化 vector_store 表
-- 维度根据你使用的模型调整，Qwen text-embedding-v3 为 1024
CREATE TABLE IF NOT EXISTS vector_store (
                                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    content TEXT NOT NULL,
    embedding VECTOR(1024),
    metadata JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
                             );

-- 6. 为向量字段创建索引（极大提升检索速度）
-- CREATE INDEX IF NOT EXISTS vector_store_embedding_idx
--     ON vector_store
--     USING ivfflat (embedding vector_cosine_ops)
--     WITH (lists = 100);

-- 7. 授权给专属用户（确保 ai_user 可以操作该数据库和表）
GRANT ALL PRIVILEGES ON DATABASE ai_demo TO ai_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ai_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO ai_user;