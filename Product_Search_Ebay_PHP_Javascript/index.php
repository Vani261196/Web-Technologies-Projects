<html>
<head>

<title>

Homework Assignment 6 
</title>
<script type="text/javascript">
	
					function Loadfunction(jsonData)
					{
						document.getElementById("ErrorMessage/Table").style.backgroundColor = 'white';
						

					jsonProductData = jsonData ;
					

					var html_text="<h1>Item Details</h1>";

					html_text += "<table border='1'>"

					ItemsDescriptionData = jsonProductData.Item;
					//document.getElementById("test").innerHTML = ItemsDescriptionData.PicturURL[0];


					var item_obj_keys = Object.keys(ItemsDescriptionData);
					
					

					var pictureURL,title,Subtitle,price,location,postal,refund,refundaccepted,ItemSpecs;

					for(var i=0 ;i< item_obj_keys.length;i++)
					{
						dataTag = item_obj_keys[i];
						



						if(dataTag=="PictureURL")
						{
							console.log(dataTag);
							pictureURL = ItemsDescriptionData[dataTag][0];



						}

						if(dataTag=="Title")
						{
							title = ItemsDescriptionData[dataTag];


						}

						if(dataTag=="Subtitle")
						{
							Subtitle = ItemsDescriptionData[dataTag];
						}
						
						if(dataTag=="CurrentPrice")
						{
							price=ItemsDescriptionData[dataTag].Value;
						}
						
						if(dataTag=="Location")
						{
							location = ItemsDescriptionData[dataTag];
						}

						if(dataTag=="PostalCode")
						{
							postal=ItemsDescriptionData[dataTag];
						}

						if(dataTag == "Seller")
						{
							seller = ItemsDescriptionData[dataTag].UserID;
						}
						if(dataTag == "ReturnPolicy")
						{
							refund = ItemsDescriptionData[dataTag].ReturnsWithin;
						
							refundaccepted = ItemsDescriptionData[dataTag].ReturnsAccepted;

						}

						if(dataTag = "ItemSpecifics")
						{
							ItemSpecs = ItemsDescriptionData[dataTag].NameValueList;
							
						}
						



					}
					
					


					html_text+="<tr height='100'><td><b>Photo</b></td> <td><img src='"+pictureURL+"' height='300' width='300' ></td></tr>";
					html_text+="<tr><td><b>Title</b></td> <td>"+title+"</td></tr>";
					html_text+="<tr><td><b>Subtitle</b></td> <td>"+Subtitle+"</td></tr>";
					html_text+="<tr><td><b>Price</b></td> <td>"+price+"</td></tr>";
					html_text+="<tr><td><b>Location</b></td> <td>"+location+", "+postal+"</td></tr>";
					html_text+="<tr><td><b>Seller</b></td> <td>"+seller+"</td></tr>";
					html_text+="<tr><td><b>Refund Polycy(US)</b></td> <td>"+refundaccepted+" within "+refund+"</td></tr>";


					for(var j=0 ; j< ItemSpecs.length ; j++)
					{


						html_text+="<tr><td><b>"+ItemSpecs[j].Name+"</b></td> <td>"+ItemSpecs[j].Value[0]+"</td></tr>";

					}
					html_text+="</table>";

					passing(html_text);

					document.getElementById("ErrorMessage/Table").innerHTML = html_text;

					document.getElementById("SellerMessage").style.visibility = 'visible';
					document.getElementById("SimilarProduct").style.visibility = 'visible';

					}



</script>



<style>
.outer_box{
	background-color: rgb(240,240,240);
	
	margin: 0 auto;
	border : solid rgb(192,192,192);
	width: 600px;
	height:400px;
}
.header_box
{

text-align: center;
margin-left: 10px;
margin-right: 10px;

}
.inputs
{
	margin-left: 25px;
	font-size: 15px;
}

table{

	
	border-collapse: collapse;
	position: relative;
	text-align: center;
	margin 0 auto;

}
table,td,th{
	border:1px solid black;

}


a:link
{
	color:black;
	text-decoration: none;
}
a:active
{
	color: black;
	text-decoration: none;
}
a:visited
{
	color:black;
	text-decoration: none;
}

a:hover
{
	color: #DCDCDC;
}




</style>

</head>




<body>

	<form method = 'GET' action="<?= $_SERVER['PHP_SELF']; ?>" id="main_from">

		<div class = 'outer_box' >

			<div class = 'header_box'>
				<p  style=" font-style: italic; font-size: 35px ; display: inline-block; margin-top: 0px; margin-bottom: 0px"> Product Search </p>
				<hr>

			</div>

			<div class="inputs">

				<b>Keyword </b> <input name="Keyword" type="text" id="Keyword" required="required">
				<br>
				<br>
				<b>Category</b>
				<select name="Category" id="Category">
					
					<option value="Art"> Art</option>
					<option value="Baby"> Baby</option>
					<option value="Books"> Books</option>
					<option value="Clothing"> Clothing</option>
					<option value="ShoesAcceessories"> Shoes & Accessories</option>
					<option value="Computers/Tablets"> Computer/Tablets</option>
					<option value="Networking"> Networking</option>
					<option value="HealthBeauty"> Health & Beauty</option>
					<option value="Music">Music</option>
					<option value="VideoGamesConsoles"> Video Games & Consoles</option>
					<option value="default" selected="">All Categories</option>



				</select>
				<br>
				<br>

				<b style="margin-right: 12">Condition</b>

				<input type="checkbox" name="condition[]" value="New"> New
				<input type="checkbox" name="condition[]" value="Used"> Used
				<input type="checkbox" name="condition[]" value="Unspecified"> Unspecified

				<br>
				<br>

				<b style="margin-right: 12">Shipping Options</b>

				<input type="checkbox" name="shipping[]" value="LocalPickupOnly"> Local Pickup
				<input type="checkbox" name="shipping[]" value="FreeShippingOnly"> Free Delivery

				<br>
				<br>

				<input type="checkbox" name="LocationSearchCheck" id="Checkid" value="LocationSearch" onclick="Location()"><b style=" margin-right: 12">Enable Nearby Search</b>
				<input type="text" name="LocationSearch" placeholder="10" id="LocationSearch" disabled="disabled"><b style="margin-left: 5">miles from </b>
				<input type="radio" name="Here" onclick = "Here()" value="Here" id="here" disabled="disabled">Here<br>
				<input type="radio" name="Here" onclick = "Zip()" value="Zip" id="not_here" disabled="disabled" style="margin-left: 419" > <input type="text" name="ZipCode" required="required" placeholder="zip code" required="required"  id="Zip_text" style="width: 130" disabled="disabled" onchange="ZipException()">

				<br>
				<br>
				<br>

				<button name="submit" id="submit_button"  style="margin-left: 210px;" disabled="disabled" >Search</button><input type="button" name="Clear" id="ClearButton" onclick="clearAll()" value="Clear">

				<input type="hidden" name="GeoLocationcode" id = "GeoLocationcode"><input type="hidden" name="Temp" id = "Temp"></div>

			</div>
		</form>

		<div style="text-align: center;" id="Data">
			<p id="ErrorMessage/Table" style="display: inline-block;"></p>
			<p id="ErrorMessage" style="display: inline-block;"></p>
			<br>
			<br>

			
			<div id="SellerMessage" style="display: inline-block; text-align: center;visibility: hidden;">

			<p id="SellerMessageText" style="color: rgb(200,200,200) ; display: inline-block;">Click to see seller message</p><br>
			<!--<img src="http://csci571.com/hw/hw6/images/arrow_down.png" name="arrowimage1" style="display: inline-block; height: 30px;">-->
			<img src="http://csci571.com/hw/hw6/images/arrow_down.png" style="display: inline-block;height: 30px;" onclick="SellerMessagefunction()" id="arrow1"><br>
			<br>
			<br>
			<iframe  srcdoc="" scrolling="no" src=""  onload='javascript:(function(o){o.style.height=o.contentWindow.document.body.scrollHeight+10+"px";}(this));'   id="SellerMessageFrame" name="SellerMessageFrame" style="display: none;border: none;width: 800px;"></iframe>
			<br>

		</div>

		<br>
		<br>
		<div id="SimilarProduct" style="display: inline-block; text-align: center;visibility: hidden;">



			<p id="Similar Products" style="color: rgb(200,200,200) ; display: inline-block;">Click to show similar items</p><br>

			<img src="http://csci571.com/hw/hw6/images/arrow_down.png" style="display: inline-block;height: 30px;" onclick="SimilarProductfunction()" id="arrow2"><br>
			<iframe  srcdoc=""  src=""   onload='javascript:(function(o){o.style.height=o.contentWindow.document.body.scrollHeight+20+"px";}(this));'  id="SimilarProductFrame" name="SimilarProductFrame" style="display: none;border: 3px solid rgb(200,200,200);height: 500px; width: 600px; overflow-x: hidden;  "></iframe><br><br>

		</div> 

		
		
		


<script type="text/javascript">

function clearAll()
{
	document.getElementById('main_from').reset();
	document.getElementById('Category').value = 'default';
	//document.getElementById("Keyword").value ="";
	/*document.getElementById("condition[1]").checked = false;
	document.getElementById("condition[2]").checked = false;
	document.getElementById("condition[3]").checked = false;
	document.getElementById("shipping[1]").checked = false;
	document.getElementById("shipping[2]").checked = false;*/
	document.getElementById("Checkid").checked = false;
	document.getElementById("LocationSearch").placeholder='10';
	document.getElementById("LocationSearch").disabled = true;
	document.getElementById("here").disabled=true;
	document.getElementById("here").checked = true;
	document.getElementById("not_here").disabled=true;
	document.getElementById("not_here").disabled=false;
	document.getElementById("Zip_text").placeholder = "zip code";
	document.getElementById("Zip_text").disabled = true;
	document.getElementById("ErrorMessage/Table").innerHTML = "";
	document.getElementById("SellerMessage").visibility= 'hidden';
	document.getElementById("SimilarProduct").visibility= 'hidden';




	

}


var xmlhttp;
document.getElementById("submit_button").disabled = true;
document.getElementById("Temp").value = 1;
if (window.XMLHttpRequest)
{// code for IE7+, Firefox, Chrome, Opera, Safari
   xmlhttp=new XMLHttpRequest();
} 
else 
{// code for IE6, IE5
	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
}
	xmlhttp.open("GET", "http://ip-api.com/json",false);
    xmlhttp.send();
	
	if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{

    var jsonObj = JSON.parse(xmlhttp.responseText);
    document.getElementById("GeoLocationcode").value = jsonObj.zip;
    //document.getElementById("GeoLocationLat").value = jsonObj.lat;
    console.log(jsonObj);
    document.getElementById("submit_button").disabled = false;

	}	


	function Here() {
		document.getElementById("here").disabled = false;
		document.getElementById("not_here").disabled = true;
		document.getElementById("Zip_text").disabled = true;
	}

	function ZipException()
	{	var bool = document.getElementById('not_here').checked;
	console.log(bool);

	if(bool)
	{
		zipcode = document.getElementById("Zip_text").value;
		console.log(zipcode);
		var isValid = /^\d{5}$|^\d{5}-\d{4}$/.test(zipcode);
		console.log(isValid);
        	if (!isValid){
       
      	document.getElementById("ErrorMessage/Table").style.backgroundColor = "#DCDCDC";
		document.getElementById("ErrorMessage/Table").style.width = "600px";
		document.getElementById("ErrorMessage/Table").innerHTML = "ZipCode is Invalid.";}

	}}

	function Zip() {
		document.getElementById("here").disabled = true;
		document.getElementById("Zip_text").disabled = false;
		zip=document.getElementById("Zip_text").value
		console.log(zip);
		//ZipException(zip);
	}

	

	function Location(){
		document.getElementById('LocationSearch').disabled = false;
		document.getElementById('here').disabled = false;
		document.getElementById('here').checked = true;
		document.getElementById('not_here').disabled = false;
		document.getElementById('Zip_text').disabled = true;
	}

	<?php
		if(isset($_GET['Category'])):
	?>
    document.getElementById('Category').value = "<?php echo $_GET['Category'];?>";
    localStorage.setItem('Category',document.getElementById("Category").value);
	<?php
	endif;
	?>

	<?php
		if(isset($_GET['Keyword'])):
	?>
    document.getElementById('Keyword').value = "<?php echo htmlentities($_GET['Keyword']);?>";
    localStorage.setItem('Keyword',document.getElementById("Keyword").value);
	<?php
	endif;
	?>

	/*<?php
		//if(isset($_GET['condition'])):

			//$n =count($_GET['condition']);
			//$Condition = $_GET['condition'];
	?>

	
	
	{
    document.getElementById('condition[1]').checked=true;
    localStorage.setItem('condition[1]',document.getElementById("condition[1]").value);
	}

	<?php
	//endif;
	?>*/

	



	function LoadSearchfunction(data)
	{


	jsonMainSearch = data;
	var flag = 0;

	try{
	var ItemsList = jsonMainSearch.findItemsAdvancedResponse[0].searchResult[0].item;
	}
	catch(error)
	{
	

		document.getElementById("ErrorMessage/Table").style.backgroundColor = "#DCDCDC";
		document.getElementById("ErrorMessage/Table").style.width = "600px";
		document.getElementById("ErrorMessage/Table").innerHTML = "No Records has been found.";
		flag=1
		ZipException();
	}



	

	
	
	
	
		

	
	//console.log(ItemsList);

	if(flag == 0)
	{
		var data_fetched = ItemsList.length;
		
	
	 if (data_fetched < 10) {
		var index = data_fetched;
	}
	else
	{
		var index = 10;
	}

		var html_text= "<table ><tr> <th>Index</th> <th>Photo</th><th>Name</th><th>Price</th><th>Zip Code</th><th>condition</th><th>Shipping Option</th></tr>";

		var photo , name , price , zip,cond, shipping;

		for(var i =0 ; i<index ; i++)
		{
			html_text+="<tr><td>" + (i+1) +"</td>";

			ItemNodeList = ItemsList[i];
			//console.log(ItemNodeList);

			var ebays_object_keys = Object.keys(ItemNodeList);

			for(var j =0 ; j < ebays_object_keys.length ; j++)
			{
				DataTag = ebays_object_keys[j];


				if(DataTag == "galleryURL")
				{
					photo = ItemNodeList[DataTag][0];

				}
				

				else if(DataTag == "title")
				{
					
					name = ItemNodeList[DataTag][0] ;

				}

				else if(DataTag == "sellingStatus")
				{
					price = ItemNodeList[DataTag][0].currentPrice[0].__value__;
									}

				else if(DataTag == "postalCode")
				{
					zip = ItemNodeList[DataTag][0];
					
				}
				//console.log(ItemNodeList[DataTag][0][conditionDisplayName][0]);

				
				else if(DataTag == "condition" && ItemNodeList[DataTag][0].conditionDisplayName[0] != null)
				{

					cond = ItemNodeList[DataTag][0].conditionDisplayName[0] ;
					
				}
				

				else if(DataTag == "shippingInfo" && ItemNodeList[DataTag][0].shippingServiceCost[0].__value__ != null)
				{
					shipping = ItemNodeList[DataTag][0].shippingServiceCost[0].__value__;

					if(shipping == 0.0)
					{
						shipping = "Free Shipping";
					}
					
				}
				



			

			}

			try	{

			html_text+="<td><img  src='"+ photo +"'></td>";

				}

			catch(error)
			{
				html_text+="<td>N/A</td>"
			}
			try	{

			html_text+="<td><a  href = 'index.php?itemid="+ItemNodeList['itemId'][0]+"'>"+name+"</a></td>";

				}

			catch(error)
			{
				html_text+="<td>N/A</td>"
			}
			try	{

			html_text+="<td>$"+price+"</td>";

				}

			catch(error)
			{
				html_text+="<td>N/A</td>"
			}
			try	{

			html_text+="<td>"+zip+"</td>";

				}

			catch(error)
			{
				html_text+="<td>N/A</td>"
			}
			try	{

			html_text+="<td>"+cond +"</td>";

				}

			catch(error)
			{
				html_text+="<td>N/A</td>"
			}
			try	{

			html_text+="<td>"+shipping +"</td>";

				}

			catch(error)
			{
				html_text+="<td>N/A</td>"
			}

				
				
				
				
				

					html_text+="</tr>";

		}
		html_text+="</table>"
		document.getElementById("ErrorMessage/Table").innerHTML = html_text;


				

							
			



		


	}
}

	function passing(htmltxt)
	{
		document.getElementById("ErrorMessage/Table").innerHTML = htmltxt;
	}
	


	
</script>

<?php

 $ebay_api_key = 'VaniSing-CSCI571H-PRD-155bfe8c7-9e50ab1c';

$Keyword = rawurlencode($_GET["Keyword"]);
$Category = $_GET["Category"];
$Condition = $_GET["condition"];
$Shipping = $_GET["shipping"];



$location = $_GET["Here"];

$flag2 = 0 ;
$flag = 0;

if(isset($_GET["LocationSearchCheck"])){

	$flag2 = 1;
	if($location != "Here") {
		
		/*try
		{*/
		

		$ZipCode = $_GET["ZipCode"];

	/*}
	catch(Exception $e)
		{
			$except = $e->getMessage();
			
		}*/

}
	else
	{
		$ZipCode = $_GET["GeoLocationcode"];
	}



	$miles = $_GET["LocationSearch"];
	if(empty($miles))
	{
		$miles = 10 ; 
	}


}
else
{
	$ZipCode = $_GET["GeoLocationcode"];
	$miles = "10";
}



/*function ValidZip($zip)
{
	if (preg_match('#[0-9]{5}#', $zip))
	{
	return true;
	}
	else
	{
	return false;
	}


}*/

$n = count($shipping);



if($n == 2)
{
	$freeflag = 'false';
	$localflag = 'false';
}
else
{

if($shipping[0] == "FreePickupOnly")
{
	
}
else if ($shipping[0] == "LocalPickupOnly") 
{
	$freeflag = 'false';
	$localflag = 'true';
}
else
{
	$freeflag = 'false';
	$localflag = 'false';

}}





if($Category == "Art")
{
	$CatId = 550;
}
if($Category == "Baby")
{
	$CatId = 2984;
}
if($Category == "Books")
{
	$CatId = 267;
}
if($Category == "Clothing")
{
	$CatId = 11450;
}
if($Category == "ShoesAcceessories")
{
	$CatId = 11450;
}
if($Category == "Computers/Tablets")
{
	$CatId = 58058;
}
if($Category == "Networking")
{
	$CatId = 58058;
}
if($Category == "HealthBeauty")
{
	$CatId = 26395;
}
if($Category == "Music")
{
	$CatId = 11233;
}
if($Category == "VideoGamesConsoles")
{
	$CatId = 1249;
}
if($Category == "default")
{
	$flag = 1;
}







if($flag2 == 1)
{

	if($flag==1)
	{
		$url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=".$ebay_api_key."&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=20&keywords=".$Keyword."&buyerPostalCode=".$ZipCode."&itemFilter(0).name=MaxDistance&itemFilter(0).value=".$miles."&itemFilter(1).name=FreePickupOnly&itemFilter(1).value=".$freeflag."&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=".$localflag;

	}
	else
	{

	$url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=".$ebay_api_key."&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=20&keywords=".$Keyword."&buyerPostalCode=".$ZipCode."&categoryId=".$CatId."&itemFilter(0).name=MaxDistance&itemFilter(0).value=".$miles."&itemFilter(1).name=FreePickupOnly&itemFilter(1).value=".$freeflag."&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=".$localflag;
	}	
}
else
{
	if($flag==1)
	{
		$url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=".$ebay_api_key."&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=20&keywords=".$Keyword."&itemFilter(0).name=MaxDistance&itemFilter(0).value=".$miles."&itemFilter(1).name=FreePickupOnly&itemFilter(1).value=".$freeflag."&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=".$localflag;

	}
	else
	{

	$url = "https://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=".$ebay_api_key."&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=20&keywords=".$Keyword."&categoryId=".$CatId."&itemFilter(0).name=MaxDistance&itemFilter(0).value=".$miles."&itemFilter(1).name=FreePickupOnly&itemFilter(1).value=".$freeflag."&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value=".$localflag;
	}

}

	



	if(!empty($Condition))
	{
		$n=count($Condition);

		for($i=0;$i<$n;$i++)
		{
			$url.="&itemFilter(3).name=Condition&itemFilter(3).value($i)=".$Condition[$i];
		}

		$count++;

	}

	//Zipcode
	//Category


	$jsonSearchData = file_get_contents($url);

	echo "<script>LoadSearchfunction($jsonSearchData);</script>";
	//$jsonSearchData = json_decode($tmpjsonSearchData,true);

	

	file_put_contents("jsonSearch.json", json_encode($jsonSearchData)); 



	if(isset($_GET['itemid'])){
					

						$itemId = $_GET['itemid'];




						 $product_details = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=".$ebay_api_key."&siteid=0&version=967&IncludeSelector=Description,ItemSpecifics,Details&ItemID=".$itemId;

						 $SimilarItems = "https://svcs.ebay.com/MerchandisingService?OPERATION-NAME=getSimilarItems&SERVICE-NAME=MerchandisingService&SERVICE-VERSION=1.1.0&CONSUMER-ID=VaniSing-CSCI571H-PRD-155bfe8c7-9e50ab1c&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&itemId=".$itemId."&maxResults=8";

						 


					$jsonProductData = file_get_contents($product_details);
					$jsonSimilarItems = file_get_contents($SimilarItems);

					$tempdecodeProductData = json_decode($jsonProductData);
					

					
					file_put_contents("jsonSearch.json", json_encode($jsonProductData));
					//file_put_contents("jasonSimilar.json", json_encode($jsonSimilarItems)); 

					echo "<script>Loadfunction($jsonProductData);</script>";


					//echo "<script>SellerMessagefunction($jsonProductData);</script>";
				}

	


?>
<script type="text/javascript">
	function SellerMessagefunction()
{

	

	document.getElementById('SellerMessageFrame').style.display = "none";
	document.getElementById('arrow2').src = "http://csci571.com/hw/hw6/images/arrow_down.png";


	var imgsrc = document.getElementById('arrow1').src;
	console.log(imgsrc);

	if(imgsrc == "http://csci571.com/hw/hw6/images/arrow_down.png")
	{
	document.getElementById('arrow1').src = "http://csci571.com/hw/hw6/images/arrow_up.png";

	try
	{

	SellerData = <?php echo $jsonProductData ?>;
	console.log(SellerData);

	jsonDescription = SellerData.Item.Description;

	if(jsonDescription == null || jsonDescription== undefined)
	{
		jsonDescription = "<p style='background-color:red ; color: black;'>No Seller Message Found</p>";
	}
}
catch(error)
{
	jsonDescription = "<p style='background-color:#DCDCDC ; color: black;'>No Seller Message Found</p>";
}

	var Iframe = document.getElementById('SellerMessageFrame');
	Iframe.style.display = 'block';
	Iframe.srcdoc = jsonDescription;




	}
	else if(imgsrc == "http://csci571.com/hw/hw6/images/arrow_up.png")
	{
		document.getElementById('arrow1').src = "http://csci571.com/hw/hw6/images/arrow_down.png";
		var Iframe = document.getElementById('SellerMessageFrame');
		Iframe.style.display = 'none';
		

	}



	


}

function SimilarProductfunction()
{

	document.getElementById("SellerMessageFrame").style.display = 'none';
	document.getElementById("arrow1").src = "http://csci571.com/hw/hw6/images/arrow_down.png";

	imgsrc = document.getElementById("arrow2").src;

	if(imgsrc == "http://csci571.com/hw/hw6/images/arrow_down.png")
	{
		document.getElementById("arrow2").src ="http://csci571.com/hw/hw6/images/arrow_up.png" ;

		try
		{

			 ProductDescription = <?php echo $jsonSimilarItems ?>;
			 console.log(ProductDescription);

			 Products = ProductDescription.getSimilarItemsResponse.itemRecommendations.item;
			 console.log(ProductDescription);
		}
		catch(error)
		{
			var html_text = "<p style='background-color: white, border: 1 px solid gray ; color: black;'>No Similar Items found</p>";
			var Iframe = document.getElementById('SimilarProductFrame');

			
			Iframe.style.display = 'block';
			Iframe.srcdoc = html_text;

		}

	var html_text ="<style>a:link{color:black;text-decoration: none;}a:active{color: black;text-decoration: none;}a:visited{color:black;text-decoration: none;}a:hover{color: #DCDCDC;}</style><table border='0' style='text-align:center'>";
	var prices = new Array(8);  
	var itemstitle = new Array(8); 
	var ids = new Array(8);                                                                                                             
	html_text+="<tr>";
	for(var i=0;i<8;i++)
	{
		ProductNodeList  = Products[i];

		prod_obj_keys = Object.keys(ProductNodeList);

		for(var j=0 ; j< prod_obj_keys.length;j++)
		{

			Tag = prod_obj_keys[j];

			if(Tag=="imageURL")
			{
				html_text+="<td><img src='"+ProductNodeList[Tag]+"'></td>";
			}
			if(Tag == "title")
			{
				itemstitle[i] = ProductNodeList[Tag];
			}

			if(Tag == "buyItNowPrice")
			{
				prices[i]= ProductNodeList[Tag].__value__;
			}
			if (Tag == "itemId") {

				ids[i]  = ProductNodeList[Tag];
			}
		}

		
	}
	html_text+="</tr>";
	html_text+="<tr>";

	for(i=0  ; i< itemstitle.length ; i++)
	{
		html_text+="<td><base target='_parent'><a href = 'index.php?itemid="+ids[i]+"'>"+itemstitle[i]+"</a></td>";

		//html_text+="<td>"+itemstitle[i]+"</td>";
	}

	html_text+="</tr>";

	html_text+="<tr>";

	for(i=0  ; i< prices.length ; i++)
	{

		html_text+="<td><b>"+prices[i]+"</b></td>";
	}
	html_text+="</tr>";

	html_text+="</table>";


	var Iframe = document.getElementById('SimilarProductFrame');

	
	Iframe.style.display = 'block';
	Iframe.srcdoc = html_text;
}
else if(imgsrc == "http://csci571.com/hw/hw6/images/arrow_up.png")
{
	document.getElementById("arrow2").src = "http://csci571.com/hw/hw6/images/arrow_down.png"
	var Iframe = document.getElementById('SimilarProductFrame');
	Iframe.style.display = 'none';
}



}




</script>
				

	
</body>
</html>