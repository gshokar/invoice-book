INSERT INTO country VALUES(1, 'CA', UPPER(UUID()), 'Canada', null);

INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (1,'ON','F88ECE60-A4EF-11E1-A74D-0250F2000001','Ontario',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (2,'BC','D794CF85-A4EF-11E1-A74D-0250F2000001','British Columbia',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (3,'AB','D794D293-A4EF-11E1-A74D-0250F2000001','Alberta',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (4,'MB','D794D2E4-A4EF-11E1-A74D-0250F2000001','Manitoba',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (5,'SK','D794D329-A4EF-11E1-A74D-0250F2000001','Saskatchewan',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (6,'QC','D794D36B-A4EF-11E1-A74D-0250F2000001','Quebec',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (7,'PE','D794D3AC-A4EF-11E1-A74D-0250F2000001','Prince Edward Island',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (8,'NS','D794D3FD-A4EF-11E1-A74D-0250F2000001','Nova Scotia',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (9,'NB','D794D455-A4EF-11E1-A74D-0250F2000001','New Brunswick',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (10,'NL','D794D497-A4EF-11E1-A74D-0250F2000001','Newfoundland and Labrador',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (11,'NT','D794D4D4-A4EF-11E1-A74D-0250F2000001','Northwest Territories',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (12,'YT','D794D512-A4EF-11E1-A74D-0250F2000001','Yukon',1);
INSERT INTO `province` (`ProvinceId`,`ProvinceCode`,`GUID`,`Province`,`CountryId`) VALUES (13,'NU','D794D550-A4EF-11E1-A74D-0250F2000001','Nunavut',1);

INSERT INTO addresstype (TypeId, `Name`, GUID, AddedBy, AddedDate, LastUpdatedBy, LastUpdatedDate) VALUES(1, 'Business', UPPER(UUID()), 0, Now(), 0, Now());
INSERT INTO contacttype (TypeId, `Name`, GUID, AddedBy, AddedDate, LastUpdatedBy, LastUpdatedDate) VALUES(1, 'Business', UPPER(UUID()), 0, Now(), 0, Now());
INSERT INTO itemunit (TypeId, `Name`, GUID, AddedBy, AddedDate, LastUpdatedBy, LastUpdatedDate) VALUES(1, 'Hour', UPPER(UUID()), 0, Now(), 0, Now());

INSERT INTO `tblsequence`(SequenceId, Filler, IdGenerator, LastNo, MaxLength, `Name`, PostFixString, PreFixString) VALUES (1,'0',NULL,100000,6,'ClientNumber',NULL,NULL);
INSERT INTO `tblsequence`(SequenceId, Filler, IdGenerator, LastNo, MaxLength, `Name`, PostFixString, PreFixString) VALUES (2,'0',NULL,100000,6,'EmployeeNumber',NULL,NULL);
INSERT INTO `tblsequence`(SequenceId, Filler, IdGenerator, LastNo, MaxLength, `Name`, PostFixString, PreFixString) VALUES (3,'0',NULL,100000,6,'ClientLocationNumber',NULL,NULL);