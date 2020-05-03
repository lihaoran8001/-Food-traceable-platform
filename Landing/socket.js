
//Block Transaction Content class 
function Content(name, id, weight, image, produceTime, perPrice, unit) {
	this.name = name;
	this.id = id;
	this.weight = weight;
	this.image = image;
	this.produceTime=produceTime;
	this.perPrice=perPrice;
	this.unit=unit;
}



Content.prototype.toString = function() {
	return "{\"name\":" + "\"" + this.name + "\"" + ", \"id\":" + "\""
			+ this.id + "\"" + ",\"weight\":" + "\"" + this.weight + "\""
			+ ",\"image\":" + "\"" + this.image + "\"" +",\"produceTime\":"+
			"\""+this.produceTime+"\""+",\"perPrice\":"+"\""+this.perPrice+"\""+",\"unit\":"+
			"\""+this.unit+"\""+"}";
}

function Transaction(sender, receiver, price, sendContents, receiveContents) {
	this.sender = sender;
	this.receiver = receiver;
	this.price = price;
	this.sendContents = new Array();
	for(o=0;o<sendContents.length;o++){
		this.sendContents[o]=new Content(sendContents[o].name,sendContents[o].id, sendContents[o].weight, sendContents[o].image, sendContents[o].produceTime, sendContents[o].perPrice, sendContents[o].unit);
	}
	this.receiveContents = new Array();
	for(e=0;e<receiveContents.length;e++){
		this.receiveContents[e]=new Content(receiveContents[e].name,receiveContents[e].id,
				receiveContents[e].weight, receiveContents[e].image, receiveContents[e].produceTime,
				receiveContents[e].perPrice, receiveContents[e].unit);
	}
}


Transaction.prototype.toString = function() {
	var sContent = "{";
	sContent = sContent + "\"size\":" + "\"" + this.sendContents.length
			+ "\",";
	var sLength = this.sendContents.length;
	for (i = 0; i < sLength; i++) {
		sContent = sContent + "\"" + i + "\":"
				+ this.sendContents[i].toString();
	}
	sContent = sContent + "}";
	var rContent = "{";
	rContent = rContent + "\"size\":" + "\"" + this.receiveContents.length
			+ "\",";
	var rLength = this.receiveContents.length;
	for (i = 0; i < rLength; i++) {
		rContent = rContent + "\"" + i + "\":"
				+ this.receiveContents[i].toString();
	}
	rContent = rContent + "}";
	var result = "{\"sender\":" + "\"" + this.sender + "\""
			+ ",\"receiver\":" + "\"" + this.receiver + "\""
			+ ",\"price\":" + "\"" + this.price + "\""
			+ ",\"sendContents\":" + sContent + ",\"receiveContents\":"
			+ rContent + "}";
	return result;
}

function Block(index, hashCode, previousHashCode, timeStamp, transaction) {
	this.index = index;
	this.hashCode = hashCode;
	this.previousHashCode = previousHashCode;
	this.timeStamp = timeStamp;
	this.transaction = transaction;
}

Block.prototype.toString = function() {
	var result = "{\"index\":" + this.index + ",\"hashCode\":" + "\""
			+ this.hashCode + "\"" + ",\"previousHashCode\":" + "\""
			+ this.previousHashCode + "\"" + ",\"timeStamp\":" + "\""
			+ this.timeStamp + "\"" + ",\"transaction\":"
			+ this.transaction.toString() + "}";
	return result;
}
function contentsFromFactory(factory,contents){
	this.factory=factory;
	this.contents=contents;
}

//对特定区块计算hash值
function hash(block){
var record=block.index+block.timeStamp+block.transaction.toString()+block.previousHashCode;
var result=md5(record);
return result;
}
//block chain utils
//通过hash值验证当前内存中的区块链的完整性
function validBlockChain(blklst){
var len=blklst.length;
var i=0;
for(i=1;i<len;i++){
	var record=blklst[i-1].index+blklst[i-1].timeStamp+blklst[i-1].transaction.toString()+blklst[i-1].previousHashCode;
	if(blklst[i].previousHashCode!=md5(record)){		
		return 0;
	}
}
return 1;
}

//根据页面输入的交易/生产信息生成区块
function generateBlock(blockList, transaction){	

var index=blockList.length;

var previousRecord=blockList[index-1].index+blockList[index-1].timeStamp+blockList[index-1].transaction.toString()
+blockList[index-1].previousHashCode;
var previousHashCode=md5(previousRecord);
var timeStamp=new Date().getTime();

var rID=transaction.receiveContents[0].id.split("-");
transaction.receiveContents[0].id=index+"-"+rID[1];

var record=index+timeStamp+transaction.toString()+previousHashCode;
var latestBlock=new Block(index,md5(record),previousHashCode, timeStamp, transaction);

return latestBlock;
}

//根据商品的id值溯源
function getSource(blklst,id,process){
//var process=new Array();
var idxarr=id.split("-");
var idx=idxarr[0];
while(true){
	if(blklst[idx-0].transaction.sender==blklst[idx-0].transaction.receiver){
		alert("加工"+blklst[idx-0].transaction.sendContents[0].id
		+"->"+blklst[idx-0].transaction.receiveContents[0].id);
	
		var cff=new contentsFromFactory(blklst[idx-0].transaction.receiver,
				blklst[idx-0].transaction.receiveContents);
		process.push(cff);
		
		var node=new Array();
		var len=blklst[idx-0].transaction.sendContents.length;
		var i=-1;
		for(i=0;i<len;i++){
			alert("i="+i+",len="+len);
			getSource(blklst,blklst[idx-0].transaction.sendContents[i].id,node);
		}
		process.push(node);
		return;
		//return process;
		//加工
	}else{
		//交易
		alert("交易"+blklst[idx-0].transaction.sendContents[0].id
				+"->"+blklst[idx-0].transaction.receiveContents[0].id);
		var cff=new contentsFromFactory(blklst[idx-0].transaction.receiver,
				blklst[idx-0].transaction.receiveContents);
		process.push(cff);
		
		var previousIdx=idx;
		idxarr=blklst[idx-0].transaction.sendContents[0].id.split("-");
		idx=idxarr[0];
		
		
		if(idx=="."){
			alert("源头");
			var sourceCff=new contentsFromFactory(blklst[previousIdx].transaction.sender,
					blklst[previousIdx].transaction.sendContents);
			process.push(sourceCff);	
			//return process;
			return;
		}
	}		
}
return process;
}


function printSource(array){
var i=-1;
for(i=0;i<array.length-1;i++){
	alert(array[i].factory+","+array[i].contents[0].toString());
}

if(array[array.length-1] instanceof Array){
	printSource(array[array.length-1]);
}else{
	alert(array[array.length-1].factory+","+array[array.length-1].contents[0].toString())
}

}
//
//从json string中解析出block对象
function getBlockFromString(str) {
var jsonObject = JSON.parse(str);
var index = jsonObject["index"];
var hashCode = jsonObject["hashCode"];
var previousHashCode = jsonObject["previousHashCode"];
var timeStamp = jsonObject["timeStamp"];
var transactionObject = jsonObject["transaction"];
var sender = transactionObject["sender"];
var receiver = transactionObject["receiver"];
var price = transactionObject["price"];
var sContentsObject = transactionObject["sendContents"];
var sLength = sContentsObject["size"];
var sendContents = new Array();
for (i = 0; i < sLength; i++) {
	var realContentObject=sContentsObject[i];
	var name = realContentObject["name"];
	var id = realContentObject["id"];
	var weight = realContentObject["weight"];
	var image = realContentObject["image"];
	var produceTime=realContentObject["produceTime"];
	var perPrice=realContentObject["perPrice"];
	var unit=realContentObject["unit"];
	var content = new Content(name, id, weight, image,produceTime,perPrice,unit);
	sendContents[i] = content;
}
var rContentsObject = transactionObject["receiveContents"];
var rLength = rContentsObject["size"];
var receiveContents = new Array();
for (i = 0; i < rLength; i++) {
	var realContentObject=rContentsObject[i];
	var name = realContentObject["name"];
	var id = realContentObject["id"];
	var weight = realContentObject["weight"];
	var image = realContentObject["image"];
	var produceTime=realContentObject["produceTime"];
	var perPrice=realContentObject["perPrice"];
	var unit=realContentObject["unit"];
	var content = new Content(name, id, weight, image,produceTime,perPrice,unit);
	receiveContents[i] = content;
}
var transaction = new Transaction(sender, receiver, price,
		sendContents, receiveContents);
var block = new Block(index, hashCode, previousHashCode, timeStamp,
		transaction);
return block;

}


//某厂商的买入记录
function buyRecored(user,blklst){
	var result=new Array();
	var i=1;
	for(i=0;i<blklst;i++){
		if((blklst[i].transaction.receiver!=blklst[i].transaction.receiver)&&(blklst[i].transaction.receiver==user)){
			result.push(blklst[i].transaction);
		}
	}
	return result;
}

//某厂商的卖出记录
function sellRecored(user,blklst){
	var result=new Array();
	var i=1;
	for(i=0;i<blklst;i++){
		if((blklst[i].transaction.receiver!=blklst[i].transaction.receiver)&&(blklst[i].transaction.sender==user)){
			result.push(blklst[i].transaction);
		}
	}
	return result;
}

//根据最新同步的区块链和用户名来计算库存
function getStock(blockChain,user){
var stock=new Array();
var len=blockChain.length;
for(i=0;i<len;i++){
	if(blockChain[i].transaction.receiver==user){
	   var flag=false;
	   for(m=0;m<stock.length;m++){
		   if(stock[m].factory==blockChain[i].transaction.sender){
			   flag=true;
			   //
			   for(n=0;n<blockChain[i].transaction.receiveContents.length;n++){
				   var exist=false;
				   for(j=0;j<stock[m].contents.length;j++){
					   var receiveID=blockChain[i].transaction.receiveContents[n].id.split("-");
					   var stockID=stock[m].contents[j].id.split("-");
					   if(receiveID[1]==stockID[1]){
						   stock[m].contents[j].weight+=blockChain[i].transaction.receiveContents[n].weight;
						   exist=true;
						   break;
					   }
				   }
				   
				   if(!exist){
					   stock[m].contents.push(blockChain[i].transaction.receiveContents[n]);
				   }
			   }
			   break;
		   }
	   }
	   if(!flag){
		   //
		   var f=blockChain[i].transaction.sender;
		   var cff=new contentsFromFactory(f,blockChain[i].transaction.receiveContents);
		   stock.push(cff);		   
	   }
	}
	if(blockChain[i].transaction.sender==user){
		for(k=0;k<blockChain[i].transaction.sendContents.length;k++){
			for(x=0;x<stock.length;x++){
				var found=false;
				for(y=0;y<stock[x].contents.length;y++){
					   var sendID=blockChain[i].transaction.sendContents[k].id.split("-");
					   var stockID=stock[x].contents[y].id.split("-");
					if(sendID[1]==stockID[1]){
						stock[x].contents[y].weight-=blockChain[i].transaction.sendContents[k].weight;
						
						if(stock[x].contents[y].weight==0){
							stock[x].contents.splice(y,1);
						}
						
						found=true;
						break;
					}
				}
				if(found){
					break;
				}
			}
	
		}
	}
	
}
return stock;
}


//根据商品id溯源
var egg=new Content("egg", ".-001", 40, "image","2019-9-4",5,"kg");
var egg1=new Content("egg", "0-001", 40, "image","2019-9-4",5,"kg");
var egg2=new Content("egg", "6-001", 20, "image","2019-9-4",5,"kg");
var milk=new Content("milk", ".-002", 40, "image","2019-9-4",5,"kg");
var milk1=new Content("milk", "3-002", 40, "image","2019-9-4",5,"kg");
var milk2=new Content("milk", "8-002", 20, "image","2019-9-4",5,"kg");
var xiaomai=new Content("xiaomai", ".-003", 40, "image","2019-9-4",5,"kg");
var xiaomai1=new Content("xiaomai", "1-003", 40, "image","2019-9-4",5,"kg");
var xiaomai2=new Content("xiaomai", "2-003", 40, "image","2019-9-4",5,"kg");
var fen=new Content("xiaomaifen", "5-005", 40, "image","2019-9-4",5,"kg");
var fen1=new Content("xiaomaifen", "7-005", 20, "image","2019-9-4",5,"kg");
var fish=new Content("fish", ".-004", 40, "image","2019-9-4",5,"kg");
var fish1=new Content("fish", "4-004", 10, "image","2019-9-4",5,"kg");
var cake=new Content("cake", "9-006", 40, "image","2019-9-4",5,"kg");
var yugan=new Content("yugan", "11-007", 5, "image","2019-9-4",5,"kg");

var sCts = new Array();
var rCts = new Array();
sCts[0] = new Content("egg", ".-001", 40, "image","2019-9-4",5,"kg");
rCts[0] = new Content("egg", ".-001", 40, "image","2019-9-4",5,"kg");
var t1 = new Transaction("laoban", "A", "50", sCts, rCts);
var record="0"+"1280977330748"+t1.toString()+"0";
var b1 = new Block("0",md5(record),"0", "1280977330748", t1);
var blklst=new Array();
blklst.push(b1);

var sCts2 = new Array();
var rCts2 = new Array();
sCts2[0] = xiaomai;
rCts2[0] = xiaomai;
var t2=new Transaction("farmer", "C", "100", sCts2, rCts2);
var newblock=generateBlock(blklst,t2);
blklst.push(newblock);


var sCts3 = new Array();
var rCts3 = new Array();
sCts3[0] = xiaomai1;
rCts3[0] = xiaomai1;
var t3=new Transaction("C", "D", "100", sCts3, rCts3);
var newblock=generateBlock(blklst,t3);
blklst.push(newblock);

var sCts4 = new Array();
var rCts4 = new Array();
sCts4[0] = milk;
rCts4[0] = milk;
var t4=new Transaction("nainong", "B", "100", sCts4, rCts4);
var newblock=generateBlock(blklst,t4);
blklst.push(newblock);

var sCts5 = new Array();
var rCts5 = new Array();
sCts5[0] = fish;
rCts5[0] = fish;
var t5=new Transaction("fisher", "E", "100", sCts5, rCts5);
var newblock=generateBlock(blklst,t5);
blklst.push(newblock);

var sCts6 = new Array();
var rCts6 = new Array();
sCts6[0] = xiaomai2;
rCts6[0] = fen;
var t6=new Transaction("D", "D", "100", sCts6, rCts6);
var newblock=generateBlock(blklst,t6);
blklst.push(newblock);

var sCts7 = new Array();
var rCts7 = new Array();
sCts7[0] = egg1;
rCts7[0] = egg1;
var t7=new Transaction("A", "factory", "100", sCts7, rCts7);
var newblock=generateBlock(blklst,t7);
blklst.push(newblock);

var sCts8 = new Array();
var rCts8 = new Array();
sCts8[0] = fen;
rCts8[0] = fen;
var t8=new Transaction("D", "factory", "100", sCts8, rCts8);
var newblock=generateBlock(blklst,t8);
blklst.push(newblock);

var sCts9 = new Array();
var rCts9 = new Array();
sCts9[0] = milk1;
rCts9[0] = milk1;
var t9=new Transaction("B", "factory", "100", sCts9, rCts9);
var newblock=generateBlock(blklst,t9);
blklst.push(newblock);

var sCts10 = new Array();
var rCts10 = new Array();
sCts10[0] = egg2;
sCts10[1] = milk2;
sCts10[2] = fen1;
rCts10[0] = cake;
var t10=new Transaction("factory", "factory", "100", sCts10, rCts10);
var newblock=generateBlock(blklst,t10);
blklst.push(newblock);

var sCts11 = new Array();
var rCts11 = new Array();
sCts11[0] = cake;
rCts11[0] = cake;
var t11=new Transaction("factory", "market", "100", sCts11, rCts11);
var newblock=generateBlock(blklst,t11);
blklst.push(newblock);

var sCts12 = new Array();
var rCts12 = new Array();
sCts12[0] = fish1;
rCts12[0] = yugan;
var t12=new Transaction("E", "E", "100", sCts12, rCts12);
var newblock=generateBlock(blklst,t12);
blklst.push(newblock);

var sCts13 = new Array();
var rCts13 = new Array();
sCts13[0] = yugan;
rCts13[0] = yugan;
var t13=new Transaction("E", "F", "100", sCts13, rCts13);
var newblock=generateBlock(blklst,t13);
blklst.push(newblock);

/*
for(i=0;i<s.length;i++){
if(s[i].contents.length!=0){
	alert(s[i].factory);
	for(j=0;j<s[i].contents.length;j++){
		alert(s[i].contents[j].toString());
	}
}
}*/

/*
var source=new Array();
getSource(blklst,"10-006",source);
printSource(source);
*/

/*

var pork = new Content("pork", "001", 40, "image","2019-9-4",5,"kg");
var beef = new Content("beef", "0-002", 20, "image","2019-9-8",5,"g");
var beefc = new Content("beef", "003", 20, "image","2019-9-12",5,"g");
var pork10 = new Content("pork", "0-001", 10, "image","2019-9-4",5,"kg");
var pork20 = new Content("pork", "0-001", 20, "image","2019-9-4",5,"kg");

var sCts = new Array();
var rCts = new Array();
sCts[0] = pork10;
rCts[0] = pork10;

var sCts2 = new Array();
var rCts2 = new Array();
sCts2[0] = pork20;
rCts2[0] = pork20;

var sCts3 = new Array();
var rCts3 = new Array();
sCts3[0] = beef;
rCts3[0] = beef;

var sCts4 = new Array();
var rCts4 = new Array();
sCts4[0] = beefc;
rCts4[0] = beefc;

var t1 = new Transaction("A", "B", "50", sCts, rCts);
var t2=new Transaction("A", "B", "100", sCts2, rCts2);
var t3=new Transaction("A","B","100",sCts3, rCts3);
var t4=new Transaction("C","B","100",sCts4, rCts4);

var t5=new Transaction("B","D","100",sCts2, rCts2);

var record="0"+"1280977330748"+t1.toString()+"0";
var b1 = new Block("0",md5(record),"0", "1280977330748", t1);
var blklst=new Array();

blklst.push(b1);

var newblock=generateBlock(blklst,t2);

blklst.push(newblock);
var newblock2=generateBlock(blklst,t3);

blklst.push(newblock2);
var newblock3=generateBlock(blklst,t4);
blklst.push(newblock3);

var newblock4=generateBlock(blklst,t5)
blklst.push(newblock4);

var s=getStock(blklst,"B");

for(i=0;i<s.length;i++){
alert(s[i].factory);
for(j=0;j<s[i].contents.length;j++){
	alert(s[i].contents[j].toString());
}
}

*/


//web socket 
var webSocket = null;
var flag = true;//全局标记位，标记浏览器是否支持websocket
var syncBlockList = new Array();
var blockList=new Array();
var blockChain= '<%=blockChainFile%>';
	var blockChainStr = blockChain.split("@");
	for (i = 0; i < blockChainStr.length - 1; i++) {
		blockList[i] = getBlockFromString(blockChainStr[i]);
	}

	$(function() {
		if (!window.WebSocket) {
			$("body").append("<h1>你的浏览器不支持WebSocket</h1>");
			flag = false;
			return;
		}

	});

	//根据所受到的区块信息，选出一个具有最长链的客户端来完成区块链同步
	function selectBlock(syncBlocklist) {
		//将数组按照index递减顺序排序
		syncBlocklist.sort();
		syncBlocklist.reverse();

		var selfIndex = blockList[blockList.length - 1].index;
		var selfHashCode = blockList[blockList.length - 1].hashCode;

		var length = syncBlocklist.length;
		var str = syncBlocklist[0];
		var splitStr = str.split(",");
		var currentIndex = splitStr[0];
		var currentHash = splitStr[1];
		var idArray = new Array();//存储所有符合条件的id
		var num = 0;
		var flag = false;
		for (i = 0; i < length; i++) {
			str = syncBlocklist[i];
			$(".infos").append("<li class='red'>当前判断的字符串：" + str + "</li>");
			splitStr = str.split(",");
			if (currentIndex == splitStr[0] && currentHash == splitStr[1]) {
				num++;
				idArray.push(splitStr[2]);

				//达到接收总数的三分之一
				if (num == length / 3) {
					if (currentIndex == selfIndex
							&& currentHash == selfHashCode) {
						return "-2";
					}
					flag = true;
				}

				if (i == length - 1 && flag) {
					var randNumber = parseInt(Math.floor(Math.random()
							* idArray.length));//生成随机数
					$(".infos").append(
							"<li class='red'>选中：" + idArray[randNumber]
									+ "</li>");
					return idArray[randNumber];
				}

			} else if (flag) {
				var randNumber = parseInt(Math.floor(Math.random()
						* idArray.length));//生成随机数
				$(".infos").append(
						"<li class='red'>选中：" + idArray[randNumber] + "</li>");
				return idArray[randNumber];
			} else {
				currentIndex = splitStr[0];
				currentHash = splitStr[1];
				num = 0;
				idArray = [];
				idArray.push(splitStr[2]);
			}
		}
		if (!flag) {
			$(".infos").append("<li class='red'>没有符合条件的区块</li>");
			return "-1";
		}
	}

	//获取旧区块中的交易信息，生成新的区块，并将其发送至服务器
	function resendBlock(oldBlock) {
		alert("重新发送");
		var newTransaction = oldBlock.transaction();//获取交易
		var newBlock = generateBlock(blockList, newTransaction);
		webSocket.send("4#" + newBlock.toString);
	}

	//开启连接
	function startConnect() {
		if (flag == false) {
			return;
		}
		var url = "ws://localhost:9999/Landing/websocket";
		webSocket = new WebSocket(url);
		webSocket.onerror = function(event) {
			onError(event)
		};
		webSocket.onopen = function(event) {
			onOpen(event)
		};
		webSocket.onmessage = function(event) {
			onMessage(event)
		};

	}

	//处理收到的消息
	//服务端收到的消息如下：
	//-请求同步：0
	//-响应同步：1#同步请求方id#最后一个区块的hash和index
	//-请求拷贝区块链：2#区块链拥有方id
	//-发送区块链：3#接收方id#区块链
	//-客户端发起交易：4#记录交易的区块
	//-客户端收到交易区块：7
	//客户端收到的消息如下（服务端会将消息来源方的id加在消息头）：
	//-请求同步：同步请求方id#0
	//-响应同步：同步响应方id#1#同步请求方id#最后一个区块
	//-请求拷贝区块链：请求方id#2#区块链拥有方id
	//-发送区块链：发送方id#3#接收方id#区块链
	//-客户端发起交易：发起方id#4#记录交易的区块
	//-服务端已接收交易：-1#5#记录交易的区块
	//-服务端正忙：-1#6#记录交易的区块
	//-交易处理完毕：-1#8
	function onMessage(event) {
		var receiveStr = event.data;//收到的消息	
		$(".infos").append("<li class='blue'>" + "收到消息" + receiveStr + "</li>");
		var splitStr = receiveStr.split("#");//将消息按#分隔后的字符串数组
		var messageHead = splitStr[1] - 0;//消息头（整数）

		//对消息进行分类处理
		switch (messageHead) {
		case 0://收到同步请求，将自己最后一个区块发送给对方
			var len = blockList.length;
			var idx = blockList[len - 1].index;
			var hashcode = blockList[len - 1].hashCode;
			var sendMessage = "1#" + splitStr[0] + "#" + idx + "," + hashcode;
			webSocket.send(sendMessage);
			break;
		case 1://收到同步响应，接收对方发来的区块
			$(".infos").append(
					"<li class='blue'>" + "收到" + splitStr[0] + "发来的区块："
							+ splitStr[3] + "</li>");
			if (syncBlockList.length <= 3) {

				syncBlockList.push(splitStr[3] + "," + splitStr[0]);

				if (syncBlockList.length == 3) {
					$(".infos").append("<li class='blue'>" + "收到3个" + "</li>");
					var result = selectBlock(syncBlockList);
					if (result == "-2") {
						$(".infos").append("<li class='red'>已同步</li>");
						break;
					} else if (result != "-1") {
						webSocket.send("2#" + result);//发送获取区块链请求
					} else {
						$(".infos").append("<li class='red'>同步失败</li>");
					}
				}
			}

			break;
		case 2://收到拷贝区块链请求
			var data = "";
			var len = blockList.length;
			for (i = 0; i < len; i++) {
				data = data + blockList[i].toString();
				data = data + "@";
			}

			var sendMessage = "3#" + splitStr[0] + "#" + data;
			webSocket.send(sendMessage);

			break;
		case 3://收到拷贝区块链响应
			var splitBlock = splitStr[3].split("@");
			var len = splitBlock.length - 1;
			for (i = 0; i < len; i++) {
				blockList[i] = getBlockFromString(splitBlock[i]);
			}

			for (i = 0; i < len; i++) {
				$(".infos").append(blockList[i].toString());
			}

			break;
		case 4://收到新区块
			var newBlock = getBlockFromString(splitStr[2]);//新收到的区块
			var lastBlock = blockList[blockList.length - 1];//当前区块链最后一个区块
			var newRecord = newBlock.index + newBlock.timeStamp
					+ newBlock.transaction.toString()
					+ newBlock.previousHashCode;
			var lastRecord = lastBlock.index + lastBlock.timeStamp
					+ lastBlock.transaction.toString()
					+ lastBlock.previousHashCode;

			//判断新区块是否正确
			if (newBlock.previousHashCode == md5(lastRecord)
					&& newBlock.index == lastBlock.index + 1
					&& newBlock.hashCode == md5(newRecord)) {
				blockList.push(newBlock);// 将新区块链上区块链
				webSocket.send("7");// 向服务端发送确认消息
			}

			break;
		case 5://服务器已接收交易区块
			blockList.push(getBlockFromString(splitStr[2]));//在自己的区块链上附上该区块，等待所有人完成同步

			break;
		case 6://服务器正在处理别的交易
			var oldBlock = getBlockFromString(splitStr[2]);//获取服务器返回的区块（即上一次发送的区块）
			setTimeout("resendBlock(oldBlock)", 1000);//等待一段时间重新发送一次交易区块

			break;
		case 8://所有人都同步了你的交易区块
			//前端页面可以显示交易已完成

			break;
		default:
			break;
		}
	}

	function onOpen(event) {
		$(".infos").append("<li class='green'>已连接至服务器</li>");
		$("#startBtn").prop("disabled", true);
		$("#sendMessageBtn").prop("disabled", false);
	}

	function onError(event) {
		$(".infos").append("<li class='red'>连接服务器发生错误</li>");
	}

	function sendMessage() {
		var msg = $("#message_input_id").val();//获取发送信息
		webSocket.send(msg);//向服务器发送消息
		$("#message_input_id").val("");//清空消息
	}

