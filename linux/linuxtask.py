# -*- coding: utf-8 -*-
__author__ = 'xiaopeng.cai'
import os
import datetime

# 替换abc to def
def replace(line):
    target = open(line, 'r')
    pre, suf = os.path.splitext(line)
    date = datetime.datetime.now().strftime('%Y_%m_%d')
    outfile = open(pre + '_' + date + suf, 'w')
    for s in target:
        outfile.write(s.replace("abc", "def"))
    target.close()
    outfile.close()

#读取all_files文件
fileList = open('/home/sean/linux_test/all_files.txt', 'r')
#新建一个files_not_found.txt
notFoundTxt = open("/home/sean/linux_test/files_not_found.txt", 'w')
for line in fileList:
    line = line.replace('\n', '')
    if os.path.isfile(line):
        replace(line)
        print('replace done! ' + line)
    else:
        notFoundTxt.write(line + "\n")
        print('file not exist:' + line)
fileList.close()
notFoundTxt.close()