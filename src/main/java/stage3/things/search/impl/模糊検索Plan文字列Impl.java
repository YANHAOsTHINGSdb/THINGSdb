package stage3.things.search.impl;

import stage3.things.search.模糊検索Plan;

public class 模糊検索Plan文字列Impl implements 模糊検索Plan {

	@Override
	public boolean 模糊chk(String str1, String str2) {
/*		//程序功能是模糊查询功能,包括连续模糊查询和间断模糊查询功能。
		//在字符串str1中寻找str2字符串,如果找到,打印str1,否则打印no.
		#include<stdio.h>
		#include<string.h>
		int main(void){
			char str1[20],str2[10]; //定义两个字符型数组
			int i,j,k,flag;
			printf("Enter str1:");
			scanf("%s",str1);
			printf("Enter str2:");
			scanf("%s",str2); //分别在内存中存入两个字符串
			i=strlen (str1); //i是字符串str1的长度
			j=strlen (str2); //j是字符串str2的长度
			flag=0; //flag的功能有二,一是计数,二是作为str2的字符元素编号。
			for(k=0;k<i;k++){
				if(str1[k]==str2[flag]) //flag初始值为0,可以从str2的第一个字符开始逐一扫描并且与str1的每一个字符比较,如果相同,flag自加1,使str2做好下一个字符的对比准备
					flag++;
			}
			if(flag==j) //如果flag与str2的长度相同,说明str1中包含str2这一子字符串,满足模糊查询条件,打印出str1
				printf("%s\n",str1);
			else
				printf("No!\n"); //否则打印no.
			return 0;
		}*/


		int i;
		int j;
		int flag=0; //flag的功能有二,一是计数,二是作为str2的字符元素编号。

		i = str1.length();
		j = str2.length();

		// flag初始值为0,可以从str2的第一个字符开始逐一扫描并且与str1的每一个字符比较,
		// 如果相同,
		// flag自加1,使str2做好下一个字符的对比准备
		for(int k=0; k<i && flag<j; k++){
			if(str1.charAt(k) == str2.charAt(flag)){
				flag++;
			}
		}
		if(flag == j){ //如果flag与str2的长度相同,说明str1中包含str2这一子字符串,满足模糊查询条件,打印出str1
			return true;
		}

		return false;
	}

}
