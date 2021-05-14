/*
 Navicat Premium Data Transfer

 Source Server         : IOTDB
 Source Server Type    : SQL Server
 Source Server Version : 11003128
 Source Host           : 192.168.160.43:1433
 Source Catalog        : IOTDB
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 11003128
 File Encoding         : 65001

 Date: 14/05/2021 22:43:48
*/


-- ----------------------------
-- Table structure for Cus_Int_IOT
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[Cus_Int_IOT]') AND type IN ('U'))
	DROP TABLE [dbo].[Cus_Int_IOT]
GO

CREATE TABLE [dbo].[Cus_Int_IOT] (
  [ID] bigint  IDENTITY(1,1) NOT NULL,
  [CreatedBy] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [CreatedOn] datetime  NULL,
  [TestBy] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [TestOn] datetime  NULL,
  [Org] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [DocNo] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [Ecorder] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [SampleNo] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [Esort] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [EItem] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [Value] decimal(24,9) DEFAULT ((0)) NULL,
  [EvaluationResult] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A1] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A2] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A3] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A4] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A5] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A6] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A7] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A8] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [A9] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [Status] int DEFAULT ((0)) NULL,
  [Mem] nvarchar(512) COLLATE Chinese_PRC_CI_AS  NULL,
  [FileType] nvarchar(50) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[Cus_Int_IOT] SET (LOCK_ESCALATION = TABLE)
GO


-- ----------------------------
-- Table structure for mts_upload_record
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[mts_upload_record]') AND type IN ('U'))
	DROP TABLE [dbo].[mts_upload_record]
GO

CREATE TABLE [dbo].[mts_upload_record] (
  [ID] bigint  IDENTITY(1,1) NOT NULL,
  [file_name] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [create_time] datetime  NULL,
  [update_time] datetime  NULL,
  [doc_no] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [sample_no] nvarchar(100) COLLATE Chinese_PRC_CI_AS  NULL,
  [upload_status] int DEFAULT ((0)) NULL,
  [upload_time] datetime  NULL,
  [data] text COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[mts_upload_record] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'0 未上报 1 已上报 2 修改需要上报',
'SCHEMA', N'dbo',
'TABLE', N'mts_upload_record',
'COLUMN', N'upload_status'
GO


-- ----------------------------
-- Indexes structure for table Cus_Int_IOT
-- ----------------------------
CREATE NONCLUSTERED INDEX [IX_Cus_Int_IOT_DocNo]
ON [dbo].[Cus_Int_IOT] (
  [DocNo] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table Cus_Int_IOT
-- ----------------------------
ALTER TABLE [dbo].[Cus_Int_IOT] ADD CONSTRAINT [pk_Cus_Int_IOT] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Indexes structure for table mts_upload_record
-- ----------------------------
CREATE NONCLUSTERED INDEX [IX_Cus_Int_IOT_DocNo_copy1]
ON [dbo].[mts_upload_record] (
  [doc_no] ASC
)
GO


-- ----------------------------
-- Primary Key structure for table mts_upload_record
-- ----------------------------
ALTER TABLE [dbo].[mts_upload_record] ADD CONSTRAINT [pk_Cus_Int_IOT_copy1] PRIMARY KEY CLUSTERED ([ID])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

