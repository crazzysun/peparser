COPY t_indicator (id, name, comment) FROM stdin;
1	SS	Standard Section Count
2	NSS	Non-Standard Section Count
3	RWX	RWX Section Count
4	XS	Executable Section Count
5	IAT	IAT Entry Count
6	HE	PE header entropy
7	CE	Code Sections Entropy
8	DE	Data Sections Entropy
9	FE	File Entropy
10	AddressSub	地址函数节点个数
11	InnerSub	内部函数节点个数
12	RemoteSub	导入名称节点个数
13	QuotientStartNodeId	入口节点序号
14	QuotientRemoteSub	节点总数/孤立导入名称节点个数
15	QuotientStartGraphNodes	有向子图的总数
16	StartNondirectedGraph	无向图数量
17	SumofAbExterSub	外部过程数量
18	SumofAbInnerSub	内部子过程数量
19	QuotientofAbsolutedNodes	孤立的节点数量
20	SumofFinalExterSub	终端外部过程
21	SumofFinalInnerSub	终端内部函数节点个数
22	MaxGraphNodes	最大有向图节点数
\.
