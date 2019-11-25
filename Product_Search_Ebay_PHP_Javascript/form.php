<html>
<head>
<title>

Homework Assignment 6 
</title>


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


</style>
<script type="text/javascript">
	
</script>
</head>




<body>

	<form method = 'POST' action="" id="main_from">

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

				<input type="checkbox" name="condition" value="New"> New
				<input type="checkbox" name="condition" value="Used"> Used
				<input type="checkbox" name="condition" value="Unspecified"> Unspecified

				<br>
				<br>

				<b style="margin-right: 12">Shipping Options</b>

				<input type="checkbox" name="shipping" value="Local"> Local Pickup
				<input type="checkbox" name="shipping" value="Free"> Free Delivery

				<br>
				<br>

				<input type="checkbox" name="LocationSearchCheck" id="Checkid" value="LocationSearch" onclick="Location()"><b style=" margin-right: 12">Enable Nearbuy Search</b>
				<input type="text" name="LocationSearch" placeholder="10" id="LocationSearch" disabled="disabled"><b style="margin-left: 5">miles from </b>
				<input type="radio" name="Here" onclick = "Here()" value="Here" id="here" disabled="disabled">Here<br>
				<input type="radio" name="Here" onclick = "Zip()" value="Zip" id="not_here" disabled="disabled" style="margin-left: 397" > <input type="text" name="ZipCode" required="required" placeholder="zip code" required="required" pattern='/^\d{1,5}$/'>

				<br>
				<br>
				<br>

				<input type="submit" name="submit" id="submit_button" value="Search" style="margin: auto 0"><input type="button" name="Clear" id="ClearButton" onclick="clearAll()" value="Clear">

				<input type="hidden" name="geolocation" id="geolocation">
				

			</div>



		</div>

	</form>
</body>
</html>