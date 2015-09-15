linux下有很多对文本进行操作的命令，比如cat filename 可以将文件的所有内容输出到控制台上。

grep keyword filename可以将文件内容中包含keyword的行内容输出到控制台上。

wc -l filename可以统计filename文件中的行数。

|是代表管道的意思，管道左边命令的结果作为管道右边命令的输入，比如cat filename | grep exception | wc -l，可以用来统计一个文件中exception出现的行数。

请实现一个功能，可以解析一个linux命令（只包含上面三个命令和上面提到的参数以及和管道一起完成的组合，其它情况不考虑），linux命令由控制台输入，并将结果输出到控制台上，比如下面这几个例子：

cat xx.txt

cat xx.txt | grep xml

wc -l xx.txt

cat xx.txt | grep xml | wc --l