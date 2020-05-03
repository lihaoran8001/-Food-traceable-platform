<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
	
<!-- 文件名称：start.jsp
  描述：注册页面
  创建日期：2019.9.1
  最后修改日期：2019.9.10
  编码人员：陈文龙，潘世康，李浩然 -->
	

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>探源</title>
<link rel="stylesheet" type="text/css" href="start.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>

<body onload="load();" onunload="unload();">

	<div id="loginPage">
		<div id="background"></div>
		<form class="form" action="user/login.action" id="loginForm"
			method="POST">
			<div>
				<input type="text" id="userName" class="input" name="userName"
					placeholder="Bussiness Name...">
			</div>

			<div>
				<input type="password" id="password" class="input" name="password"
					placeholder="Password...">
			</div>

			<div>
				<input type="file" id="fileSelect" class="input" value="选择区块链文件" />
				<div id="file" class="input">点此选择区块链文件！</div>
			</div>
			<div>
				<input type="submit" id="loginButton" value="登录"
					onclick="return check()">
			</div>
		</form>
		<button id="cancelButton">取消</button>
	</div>



	<div id="canvas">
		<button id="index" class="buttonCanvas">首页</button>
		<button id="login" class="buttonCanvas">登录</button>
		<button id="register" class="buttonCanvas">注册</button>
	</div>

	<div id="mark">
		<div id="markPhoto"></div>
	</div>

	<div id="searchField">
		<form id="search2">
			<input id="search_text" type="text" placeholder="要查找的商品编号" autofocus
				required> <input type="button" id="search_sumbit" value="查找"
				onclick="search()">
		</form>
	</div>

	<div id="result"></div>

	<div id="others">

		<a class="otherButtons" href="" id="aboutUs">关于我们</a> <a
			class="otherButtons" href="register.html" id="register">企业注册认证</a> <a
			class="otherButtons" href="">About Landing</a> <a
			class="otherButtons" href="">意见反馈</a> <a class="otherButtons" href="">联系我们</a>
	</div>

	<div>
		<div id="showFile" name="showFile" class="form-control"
			maxlength="10000" style='width: 500px; resize: none;'
			placeholder="文本输入"></div>
	</div>

</body>

<script type="text/javascript">
	$("#register").click(function() {
		window.location.href = 'register.html';
	})
	$("#login").click(function() {
		$("body").css('background', '#ABABAB');
		$("#loginPage").css('visibility', 'visible');
	});
	$("#cancelButton").click(function() {
		document.getElementById("showFile").innerHTML ="";
		document.getElementById("userName").value ="";
		document.getElementById("password").value ="";
		document.getElementById("fileSelect").value ="";
		$("body").css('background', '#FFFFFF');
		$("#loginPage").css('visibility', 'hidden');
	});

	

</script>
<script type="text/javascript">
	/**
	 * @methodsName: $("#file").click
	 * @description: 点击选择文件按钮触发事件
	 * @param: void
	 * @return: void
	 * @throws: 
	 */
	$("#file").click(function() {
		$("#fileSelect").click();
	});

	/**
	 * @methodsName: $("#fileSelect").click
	 * @description: 选择文件事件
	 * @param: void
	 * @return: void
	 * @throws: 
	 */
	$("#fileSelect").change(function() {
		var fileSelector = $("#fileSelect")[0].files;
		var file = fileSelector[0];

		var reader = new FileReader();
		reader.onload = function() {
			document.getElementById("showFile").innerHTML = this.result;
		};

		reader.readAsText(file);
	});

	/**
	 * @methodsName: transmit
	 * @description: 向服务端传输区块链文件
	 * @param: void
	 * @return: void
	 * @throws: 
	 */
	function transmit() {
		$.ajax({
			url : "user/transmit.action",
			type : "POST",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",

			data : JSON.stringify({
				userName : document.getElementById("showFile").innerHTML
			}),
			success : function(result) {

			}
		});
	}

	/**
	 * @methodsName: check
	 * @description: 检测表单输入项是否为空或包含空格
	 * @param: void
	 * @return: void
	 * @throws: 
	 */
	function check() {
		var myform = document.getElementById("loginForm");// 获得form表单对象
		for (var i = 0; i < loginForm.length - 1; i++) {// 循环form表单
			var str = myform.elements[i].value;
			if (str == "" || str.indexOf(" ") != -1) {// 判断每一个元素是否为空或有空格
				alert(myform.elements[i].id + "不能为空或包含空格！");
				myform.elements[i].focus(); // 元素获得焦点
				return false;
			}
		}
		myform.elements[1].value = md5(myform.elements[1].value);
		transmit();
		webSocket.close();//跳转页面时关闭websocket
		return true;
	}
</script>
<script>
	
</script>
<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js"></script>

<script type="text/javascript" src="jquery-1.8.0.js"></script>
<script type="text/javascript">
	/**
	 * @methodsName: Content
	 * @description: Content类的构造函数
	 * @param: name,id,weight,image,produceTime,perPrice,unit
	 * @return: void
	 * @throws: 
	 */
	function Content(name, id, weight, image, produceTime, perPrice, unit) {
		this.name = name;
		this.id = id;
		this.weight = weight;
		this.image = image;
		this.produceTime = produceTime;
		this.perPrice = perPrice;
		this.unit = unit;
	}

	/**
	 * @methodsName: Content.prototype.toString
	 * @description: 将Content对象的信息转为字符串
	 * @param: void
	 * @return: String
	 * @throws: 
	 */
	Content.prototype.toString = function() {
		return "{\"name\":" + "\"" + this.name + "\"" + ", \"id\":" + "\""
				+ this.id + "\"" + ",\"weight\":" + "\"" + this.weight + "\""
				+ ",\"image\":" + "\"" + this.image + "\""
				+ ",\"produceTime\":" + "\"" + this.produceTime + "\""
				+ ",\"perPrice\":" + "\"" + this.perPrice + "\"" + ",\"unit\":"
				+ "\"" + this.unit + "\"" + "}";
	}
	
	/**
	 * @methodsName: Transaction
	 * @description: Transaction类的构造函数
	 * @param: sender, receiver, price, sendContents, receiveContents
	 * @return: void
	 * @throws: 
	 */
	function Transaction(sender, receiver, price, sendContents, receiveContents) {
		this.sender = sender;
		this.receiver = receiver;
		this.price = price;
		this.sendContents = new Array();
		for (o = 0; o < sendContents.length; o++) {
			this.sendContents[o] = new Content(sendContents[o].name,
					sendContents[o].id, sendContents[o].weight,
					sendContents[o].image, sendContents[o].produceTime,
					sendContents[o].perPrice, sendContents[o].unit);
		}
		this.receiveContents = new Array();
		for (e = 0; e < receiveContents.length; e++) {
			this.receiveContents[e] = new Content(receiveContents[e].name,
					receiveContents[e].id, receiveContents[e].weight,
					receiveContents[e].image, receiveContents[e].produceTime,
					receiveContents[e].perPrice, receiveContents[e].unit);
		}
	}
	
	/**
	 * @methodsName: Transaction.prototype.toString
	 * @description: 将Transaction对象的信息转为字符串
	 * @param: void
	 * @return: String
	 * @throws: 
	 */
	Transaction.prototype.toString = function() {
		var sContent = "{";
		sContent = sContent + "\"size\":" + "\"" + this.sendContents.length
				+ "\"";
		var sLength = this.sendContents.length;
		for (i = 0; i < sLength; i++) {
			sContent = sContent + ",\"" + i + "\":"
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
	
	/**
	 * @methodsName: Block
	 * @description: Block类的构造函数
	 * @param: index, hashCode, previousHashCode, timeStamp, transaction
	 * @return: void
	 * @throws: 
	 */
	function Block(index, hashCode, previousHashCode, timeStamp, transaction) {
		this.index = index;
		this.hashCode = hashCode;
		this.previousHashCode = previousHashCode;
		this.timeStamp = timeStamp;
		this.transaction = transaction;
	}

	/**
	 * @methodsName: Block.prototype.toString
	 * @description: 将Block对象的信息转为字符串
	 * @param: void
	 * @return: String
	 * @throws: 
	 */
	Block.prototype.toString = function() {
		var result = "{\"index\":" + this.index + ",\"hashCode\":" + "\""
				+ this.hashCode + "\"" + ",\"previousHashCode\":" + "\""
				+ this.previousHashCode + "\"" + ",\"timeStamp\":" + "\""
				+ this.timeStamp + "\"" + ",\"transaction\":"
				+ this.transaction.toString() + "}";
		return result;
	}

	/**
	 * @methodsName: contentsFromFactory
	 * @description: contentsFromFactory类的构造函数
	 * @param: factory,contents
	 * @return: void
	 * @throws: 
	 */
	function contentsFromFactory(factory, contents) {
		this.factory = factory;
		this.contents = contents;
	}
	
	/**
	 * @methodsName: contentsFromFactorySource
	 * @description: contentsFromFactorySource类的构造函数
	 * @param: factory,contents,num
	 * @return: void
	 * @throws: 
	 */
	function contentsFromFactorySource(factory, contents, num) {
		this.factory = factory;
		this.contents = contents;
		this.num=num;
	}
</script>

<script type="text/javascript">
	/**
	 * @methodsName: hash
	 * @description: 对特定区块计算hash值
	 * @param: block
	 * @return: String
	 * @throws: 
	 */
	function hash(block) {
		var record = block.index + block.timeStamp
				+ block.transaction.toString() + block.previousHashCode;
		var result = md5(record);
		return result;
	}

	/**
	 * @methodsName: validBlockChain
	 * @description: 通过hash值验证当前内存中的区块链的完整性
	 * @param: blklst
	 * @return: int
	 * @throws: 
	 */
	function validBlockChain(blklst) {
		var len = blklst.length;
		var i = 0;
		for (i = 1; i < len; i++) {
			var record = blklst[i - 1].index + blklst[i - 1].timeStamp
					+ blklst[i - 1].transaction.toString()
					+ blklst[i - 1].previousHashCode;
			if (blklst[i].previousHashCode != md5(record)) {
				return 0;
			}
		}
		return 1;
	}


	/**
	 * @methodsName: generateBlock
	 * @description: 根据页面输入的交易/生产信息生成区块
	 * @param: blockList, transaction
	 * @return: Block
	 * @throws: 
	 */
	function generateBlock(blockList, transaction){	
	var newIndex=blockList.length;
	var previousRecord=blockList[newIndex-1].index+""+blockList[newIndex-1].timeStamp+blockList[newIndex-1].transaction.toString()
						+blockList[newIndex-1].previousHashCode;
	var previousHashCode=md5(previousRecord);
	var timeStamp=new Date().getTime();

	var rID=transaction.receiveContents[0].id.split("-");

	transaction.receiveContents[0].id=newIndex+"-"+rID[1];


	var record=newIndex+""+timeStamp+transaction.toString()+previousHashCode;
	var latestBlock=new Block(newIndex,md5(record),previousHashCode, timeStamp, transaction);

	return latestBlock;
	}

	/**
	 * @methodsName: getSource
	 * @description: 根据商品的id值溯源
	 * @param: blklst,id,process
	 * @return: Array
	 * @throws: 
	 */
	function getSource(blklst, id, process) {
		var idxarr = id.split("-");
		if (idxarr.length == 2) {
			var idx = idxarr[0];
			while (true) {
				if (blklst[idx - 0].transaction.sender == blklst[idx - 0].transaction.receiver) {
					//加工
					var cff = new contentsFromFactory(
							blklst[idx - 0].transaction.receiver,
							blklst[idx - 0].transaction.receiveContents);
					process.push(cff);

					var node = new Array();
					var i = -1;
					var len = blklst[idx - 0].transaction.sendContents.length;
					
					for(i = 0; i < len; i++){
						node.push(new Array());
					}
								
					for (i = 0; i < len; i++) {
						getSource(blklst,
								blklst[idx - 0].transaction.sendContents[i].id,
								node[i]);
					}
					process.push(node);
					return;
				} else {
					//交易
					var cff = new contentsFromFactory(
							blklst[idx - 0].transaction.receiver,
							blklst[idx - 0].transaction.receiveContents);
					process.push(cff);
					var previousIdx = idx;
					idxarr = blklst[idx - 0].transaction.sendContents[0].id
							.split("-");
					idx = idxarr[0];

					if (idx == ".") {
						var sourceCff = new contentsFromFactory(
								blklst[previousIdx].transaction.sender,
								blklst[previousIdx].transaction.sendContents);
						process.push(sourceCff);
						return;
					}
				}
			}
		}
		return process;
	}

	/**
	 * @methodsName: getBlockFromString
	 * @description: 从json string中解析出block对象
	 * @param: str
	 * @return: Block
	 * @throws: 
	 */
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

		var i = 0;
		for (i = 0; i < sLength; i++) {
			var realContentObject = sContentsObject[i];
			var name = realContentObject["name"];
			var id = realContentObject["id"];
			var weight = realContentObject["weight"];
			var image = realContentObject["image"];
			var produceTime = realContentObject["produceTime"];
			var perPrice = realContentObject["perPrice"];
			var unit = realContentObject["unit"];
			var content = new Content(name, id, weight, image, produceTime,
					perPrice, unit);
			sendContents[i] = content;
		}

		var rContentsObject = transactionObject["receiveContents"];
		var rLength = rContentsObject["size"];
		var receiveContents = new Array();

		var j = 0;
		for (j = 0; j < rLength; j++) {
			var realContentObject = rContentsObject[j];
			var name = realContentObject["name"];
			var id = realContentObject["id"];
			var weight = realContentObject["weight"];
			var image = realContentObject["image"];
			var produceTime = realContentObject["produceTime"];
			var perPrice = realContentObject["perPrice"];
			var unit = realContentObject["unit"];
			var content = new Content(name, id, weight, image, produceTime,
					perPrice, unit);
			receiveContents[j] = content;
		}
		var transaction = new Transaction(sender, receiver, price,
				sendContents, receiveContents);
		var block = new Block(index, hashCode, previousHashCode, timeStamp,
				transaction);
		return block;
	}
</script>

<script type="text/javascript">
	var webSocket = null;
	var syncBlockList = new Array();
	var flag = true;//全局标记位，标记浏览器是否支持websocket
	var connected = false;
	var code = "";//商品编码

	
	/**
	 * @methodsName: load
	 * @description: 开启页面时与服务端建立连接
	 * @param: void
	 * @return: void
	 * @throws: 
	 */
	function load() {
		$("#showFile").css('visibility','hidden');
		if (!window.WebSocket) {
			alert("你的浏览器不支持WebSocket");
			flag = false;
			return;
		}

		if (!connected) {
			var url = "ws://landing.chinaeast.cloudapp.chinacloudapi.cn:9999/Landing/websocket";
			//ws://landing.chinaeast.cloudapp.chinacloudapi.cn:9999/Landing/websocket
			webSocket = new WebSocket(url);
		}

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

	/**
	 * @methodsName: unload
	 * @description: 关闭页面时与服务端断开连接
	 * @param: void
	 * @return: void
	 * @throws: 
	 */
	function unload() {
		webSocket.close();
	}

	/**
	 * @methodsName: selectBlock
	 * @description: 根据所受到的区块信息，选出一个具有最长链的客户端来完成区块链同步
	 * @param: syncBlocklist
	 * @return: String
	 * @throws: 
 	 */
	function selectBlock(syncBlocklist) {
		//将数组按照index递减顺序排序
		syncBlocklist.sort();
		syncBlocklist.reverse();

		var length = syncBlocklist.length;
		var str = syncBlocklist[0];
		var splitStr = str.split(",");
		var currentIndex = splitStr[0];
		var currentHash = splitStr[1];
		var idArray = [];//存储所有符合条件的id
		var num = 0;
		var flag = false;
		for (i = 0; i < length; i++) {
			str = syncBlocklist[i];
			splitStr = str.split(",");
			if (currentIndex == splitStr[0] && currentHash == splitStr[1]) {
				num++;
				idArray.push(splitStr[2]);

				//达到接收总数的三分之一
				if (num == length / 3) {
					flag = true;
				}

				if (i == length - 1 && flag) {
					var randNumber = parseInt(Math.floor(Math.random()
							* idArray.length));//生成随机数
					return idArray[randNumber];
				}

			} else if (flag) {
				var randNumber = parseInt(Math.floor(Math.random()
						* idArray.length));//生成随机数
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
			return "-1";
		}

	}

	/**
	 * @methodsName: search
	 * @description: 向服务端发送消息以完成商品溯源
	 * @param: void
	 * @return: void
	 * @throws: 
	 */
	function search() {
		document.getElementById("result").innerHTML = "";
		code = document.getElementById("search2").elements[0].value;
		if (code != "") {
			if (flag == false) {
				document.getElementById("result").innerHTML += "你的浏览器不支持WebSocket<br/>";
				return;
			}

			webSocket.send("0");//发送同步请求
			document.getElementById("result").innerHTML += "查询中，请稍后...<br/>";		
		}	
	}
	
	/**
	 * @methodsName: timestampToTime
	 * @description: 将时间戳转换为日期格式
	 * @param: timestamp
	 * @return: String
	 * @throws: 
	 */
	function timestampToTime(timestamp) {
		var newDate = new Date();
		newDate.setTime(timestamp);
		return newDate.toLocaleString();
    }
 
	/**
	 * @methodsName: printSource
	 * @description: 显示溯源结果
	 * @param: array
	 * @return: void
	 * @throws: 
	 */
	function printSource(array) {
		var i = -1;
		for (i = 0; i < array.length - 1; i++) {
			if(i==array.length-2){
				var infoBlock = '<div class="normalBlock">'+"商品信息：<br/>"
					+ "-商品名称：" + array[i].contents[0].name + "<br/>" + "-商品编号："
					+ array[i].contents[0].id + "<br/>" + "-所属公司："
					+ array[i].factory + "<br/>" + "-生产日期："
					+ timestampToTime(array[i].contents[0].produceTime) + "<br/>" + "<br/>" +'</div>';//块
				$("#result").append(infoBlock);
			}
			else{
				var infoBlock = '<div class="normalBlock">'+"商品信息：<br/>"
				+ "-商品名称：" + array[i].contents[0].name + "<br/>" + "-商品编号："
				+ array[i].contents[0].id + "<br/>" + "-所属公司："
				+ array[i].factory + "<br/>" + "-生产日期："
				+ timestampToTime(array[i].contents[0].produceTime) + "<br/>" + "<br/>"  +'</div><div class="jiantou"></div>';//块和箭头
				$("#result").append(infoBlock);
			}
		}

		if (array[array.length - 1] instanceof Array) {
			var infoBlock ='<div class="jiantou"></div>';
			infoBlock+='<div class="specialBlocks">';
			
				for(i=0;i<array[array.length - 1].length;i++){
					var specialBlock = '<div class="specialBlock">'+
					"商品信息：<br/>"
					+ "-商品名称：" + array[array.length - 1][i][0].contents[0].name + "<br/>" + "-商品编号："
					+ array[array.length - 1][i][0].contents[0].id + "<br/>" + "-所属公司："
					+ array[array.length - 1][i][0].factory + "<br/>" + "-生产日期："
					+ timestampToTime(array[array.length - 1][i][0].contents[0].produceTime) + "<br/>" + "<br/>" +
					
					
					'</div>';
					
					infoBlock+=specialBlock;
				}
			infoBlock+='</div>';
			$("#result").append(infoBlock);
		
			var j=0;
			for(j=0;j<array[array.length - 1].length;j++){
				printSource(array[array.length - 1][j]);
			}
			
		} else {
			var infoBlock ='<div class="jiantou"></div><div class="veryBlock">'+
			"商品信息：<br/>"
			+ "-商品名称：" +array[array.length - 1].contents[0].name + "<br/>" + "-商品编号："
			+ array[array.length - 1].contents[0].id + "<br/>" + "-所属公司："
			+ array[array.length - 1].factory + "<br/>" + "-生产日期："
			+ timestampToTime(array[array.length - 1].contents[0].produceTime) + "<br/>" + "<br/>"
			+'</div>';
			$("#result").append(infoBlock);
		}

	}

	/**
	 * @methodsName: onOpen
	 * @description: 处理与服务端的连接
	 * @param: event
	 * @return: void
	 * @throws: 
	 */
	function onOpen(event) {
		connected = true;
	}

	/**
	 * @methodsName: onError
	 * @description: 处理与服务端的连接错误
	 * @param: event
	 * @return: void
	 * @throws: 
	 */
	function onError(event) {
		connected = false;
	}

	/**
	 * @methodsName: onMessage
	 * @description: 处理服务端发送或转发的消息
	 * @param: event
	 * @return: void
	 * @throws: 
	 */
	function onMessage(event) {
		 /**
		 * 客户端收到的消息如下：
		 * -响应同步：同步响应方id#1#同步请求方id#最后一个区块
		 * -发送区块链：发送方id#3#接收方id#区块链
		 * -客户端发起交易：发起方id#4#记录交易的区块
		 */
		var receiveStr = event.data;//收到的消息
		var splitStr = receiveStr.split("#");//将消息按#分隔后的字符串数组
		var messageHead = splitStr[1] - 0;//消息头（整数）
		if (messageHead == 1) {//收到区块
			if (syncBlockList.length <= 3) {
				syncBlockList.push(splitStr[3] + "," + splitStr[0]);

				if (syncBlockList.length == 3) {
					var result = selectBlock(syncBlockList);
					syncBlockList = [];
					if (result != "-1") {
						webSocket.send("2#" + result);//发送获取区块链请求
					} else {
						document.getElementById("result").innerHTML = "查询失败<br/>";
					}
				}
			}
		} else if (messageHead == 3) {//收到区块链
			var splitBlock = splitStr[3].split("@");
			var len = splitBlock.length - 1;
			var blockList = new Array();
			var i = 0;
			for (i = 0; i < len; i++) {
				blockList[i] = getBlockFromString(splitBlock[i]);
			}

			var source = new Array();
			getSource(blockList, code, source);
			document.getElementById("result").innerHTML = "";
			if(source.length==0){
				document.getElementById("result").innerHTML = "不存在该商品";
			}else{
				printSource(source);
			}	
		} else if (messageHead == 4) {//收到交易消息
			webSocket.send("7");//直接向服务器发送确认消息，相当于不做处理
		}
	}
	
</script>
</html>
