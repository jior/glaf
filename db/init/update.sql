
update SYS_TREE set DISCRIMINATOR='D' where DISCRIMINATOR is null
and ID in (
  select T.ID from SYS_TREE T
  inner join SYS_DEPARTMENT X
  on T.ID = X.NODEID
);


update SYS_TREE set DISCRIMINATOR='A' where DISCRIMINATOR is null
and ID in (
  select T.ID from SYS_TREE T
  inner join SYS_APPLICATION X
  on T.ID = X.NODEID
);


update SYS_TREE set DISCRIMINATOR='P' where DISCRIMINATOR is null
and ID in (
  select T.ID from SYS_TREE T
  inner join SYS_TEMPLATE X
  on T.ID = X.NODEID_
);


update SYS_TREE set DISCRIMINATOR='Y' where DISCRIMINATOR is null
and ID in (
  select T.ID from SYS_TREE T
  inner join SYS_DICTORY X
  on T.ID = X.TYPEID
);

update SYS_APPLICATION set locked = 0 where locked is null;

update SYS_TREE set locked = 0 where locked is null;
