
	var app = angular.module("myApp",['ui.bootstrap','angular-svg-round-progressbar']);

	app.controller('myBodyController',function($scope,$http,$timeout){

		$timeout(function() {
		    $('[data-toggle="tooltip"]').each(function(index) {
		      // sometimes the title is blank for no apparent reason. don't override in these cases.
		      if ($(this).attr("title").length > 0) {
		        $( this ).attr("data-original-title", $(this).attr("title"));
		      }
		    });
		    $timeout(function() {
		      // finally, activate the tooltips
		      $(document).tooltip({ selector: '[data-toggle="tooltip"]'});
		    }, 500);
		}, 1500);



		$scope.progress = false; 
		
		$scope.table_data = "";
		$scope.CatId = "";
		$scope.Category = "default";

		$scope.ebay_api_key = 'VaniSing-CSCI571H-PRD-155bfe8c7-9e50ab1c';
		$scope.wishItems = [];
		$scope.sortorder = "";
		$scope.WishLength = 0;
		$scope.details_disabled = true;
		$scope.distance = "here";




		$scope.isDisabled = true;


		$http.get("http://ip-api.com/json").then(

			function(response)
			{
				//console.log(response);
				$scope.zip = response.data.zip;

				console.log($scope.zip);
				$scope.isDisabled = false;
			}


			);

		$scope.flag = 0;
		

		$scope.search = function()
		{
			//console.log("hi");
				$scope.progress = true ;

				if($scope.Category == "Art")
				{
					$scope.CatId = "550";
				}
				if($scope.Category == "Baby")
				{
					$scope.CatId = "2984";
				}
				if($scope.Category == "Books")
				{
					$scope.CatId = "267";
				}
				if($scope.Category == "Clothing")
				{
					$scope.CatId = "11450";
				}
				if($scope.Category == "Computers/Tablets")
				{
					$scope.CatId = "58058";
				}
				if($scope.Category == "Networking")
				{
					$scope.CatId = "58058";
				}
				if($scope.Category == "Health Beauty")
				{
					$scope.CatId = "26395";
				}
				if($scope.Category == "Music")
				{
					$scope.CatId = "11233";
				}
				if($scope.Category == "VideoGamesConsole")
				{
					$scope.CatId = "1249";
				}

				if($scope.Category == "default")
				{
					
					$scope.CatId = '0';
				}


				
				/*

				param.Keyword = $scope.Keyword;
				param.CatId = $scope.CatId;
				param.condition1 = $scope.condition1;*/

				

				if($scope.miles == undefined)
				{
					$scope.miles = 10 ;
				}
			

				 $scope.url = "?Keyword="+$scope.Keyword+"&CatId="+$scope.CatId+"&New="+$scope.condition1+"&Used="+$scope.condition2+"&Unspecified="+$scope.condition3 + "&local="+$scope.local+"&free="+$scope.free+"&miles="+$scope.miles ;


				
					if($scope.distance == 'here')
					{
						$scope.url+= "&ZipCode="+$scope.zip;
					}
					else

					{
						$scope.url+= "&ZipCode="+$scope.distance_text;
					}



				console.log("https://vanisingalaebayproductsearch.appspot.com/form"+$scope.url);
				
				




				$http({
      method: 'GET',
      url: "https://vanisingalaebayproductsearch.appspot.com/form"+$scope.url
   }).then(function(response) 
				{
					console.log("Inside Form 1");
					console.log(response);

					$scope.table_data = response.data.findItemsAdvancedResponse[0].searchResult[0].item;
					console.log($scope.table_data);

					$scope.progress = false;
					$scope.show_details = true;

					$scope.viewby = 10;
					$scope.totalItems = $scope.table_data.length;
					$scope.currentPage = 1;
					$scope.itemsPerPage = $scope.viewby;
					$scope.maxSize = 5; //Number of pager buttons to show

					$scope.setPage = function (pageNo) {
					    $scope.currentPage = pageNo;
					};

					$scope.pageChanged = function() {
					    console.log('Page changed to: ' + $scope.currentPage);
					};

					$scope.setItemsPerPage = function(num) {
					  $scope.itemsPerPage = num;
					  $scope.currentPage = 1; //reset to first page
					}

				
					
				},function(error){
					
					console.log("error");
				});



   				var photo_url = "?product_title="+$scope.Keyword;
			
				console.log("https://vanisingalaebayproductsearch.appspot.com/product_photos"+photo_url);




  				 $http({
					      method: 'GET',
					      url: "https://vanisingalaebayproductsearch.appspot.com/product_photos"+photo_url,
					   }).then(function(response) 
				{


					console.log("Inside Photos");
					console.log(response);


					$scope.photos= response.data.items;


					var length = $scope.photos.length;
					console.log(length);

					$scope.photoslist=[];

					for(var i =0 ; i<length ; i++)
					{
						$scope.photoslist.push($scope.photos[i].link);

					}


					$scope.image_set1 = $scope.photoslist.slice(0,3);
					$scope.image_set2 = $scope.photoslist.slice(3,6);
					$scope.image_set3 = $scope.photoslist.slice(6,8);

					
				});

		}
 
		//after deploying 

	$scope.facebooktab = function()
	{
	var quoteVal = "Buy "+$scope.dataItem.title[0]+" at "+$scope.dataItem.sellingStatus[0].currentPrice[0]._value_+" from link below.";;
   		FB.ui({
    	method: 'share',
    	display: 'popup',
    	href: dataItem.viewItemURL[0],
    	quote: quoteVal
  	}, function(response){});
	}

	$scope.DetailsTab = function()
	{
		 $scope.products_show = true;
					 $scope.show_details_state = $scope.show_details;
					 $scope.wish_state = $scope.show_wishlist
					 $scope.show_details = false;
					 $scope.show_wishlist = false;

	}

	  $scope.productDetailsTab  = function(data)
		{
			

			$scope.details_disabled  = false;
			try
			{
			$scope.dataItem = data;
			$scope.SellerInfo = data.sellerInfo[0];
			$scope.storeInfo = data.storeInfo[0];
			$scope.prodtitle = data.title[0]
		}
		catch(errr)
		{}
			console.log($scope.prodtitle);


			var url2 = "?ItemId="+data.itemId[0];
			console.log("https://vanisingalaebayproductsearch.appspot.com/product_details"+url2);
			



				$http({
					      method: 'GET',
					      url: "https://vanisingalaebayproductsearch.appspot.com/product_details"+url2,
					   }).then(function(response) 
				{

					console.log("Inside ProductInfo");
					$scope.productdata = response.data;
					console.log($scope.productdata);
					$scope.modal_photos = response.data.Item.PictureURL;

					$scope.modal_photos_length = $scope.modal_photos.length;
					$scope.range = [1,2,3,4,5,6,7,8,9,10];



					
				});


				$http({
					      method: 'GET',
					      url: "https://vanisingalaebayproductsearch.appspot.com/similar_products"+url2,
					   }).then(function(response) 
				{

					console.log("Inside Similar Product Info");
					$scope.similarproductdata = response.data.getSimilarItemsResponse.itemRecommendations.item;

					console.log(response);
					
	
				});







				

				$scope.product_table_data = data.shippingInfo[0]; 

				try
				{

				if($scope.product_table_data.handlingTime[0] > 1)
				{
					$scope.daysappend = "Days";
				}
				else
				{
					$scope.daysappend = "Day";
				}
			}

			catch(error)
			{

			}

				console.log("Shipping Info");

				console.log( $scope.product_table_data);

				$scope.product_seller_info = data.Seller;
				$scope.product_store_info = data.Storefront;





				//for getting product related photos

					 $scope.products_show = true;
					 $scope.show_details_state = $scope.show_details;
					 $scope.wish_state = $scope.show_wishlist
					 $scope.show_details = false;
					 $scope.show_wishlist = false;

				//for getting product details

				
			}

			$scope.results = function()
			{
				$scope.show_details = true;
				$scope.show_wishlist = false;
				$scope.products_show = false;

			}

			$scope.list = function()
			{

				$scope.show_wishlist = $scope.wish_state;
				$scope.show_details = $scope.show_details_state;

				$scope.products_show = false;

				console.log($scope.show_wishlist , $scope.show_details);

			}


			localStorage.setItem("WishItems" , JSON.stringify($scope.wishItems));

			$scope.set_shop_cart = function(x)
			{

				var Wishlist_stored = JSON.parse(localStorage.getItem("WishItems"));

				$scope.WishLength = Wishlist_stored.length; 

				var flag_found = false ;

				for(var i = 0 ; i < Wishlist_stored.length ; i++)
				{
					

					if(Wishlist_stored[i].itemId[0] == x.itemId[0])
					{
						
						flag_found = true;
						break;

					}

				}

				if(flag_found)
				{
					$scope.wishItems.splice(i,1);
					localStorage.removeItem("WishItems");
					localStorage.setItem("WishItems", JSON.stringify($scope.wishItems));
					$scope.WishLength -= 1 ; 
				}
				else
				{
					$scope.wishItems.push(x);
					localStorage.removeItem("WishItems");
					localStorage.setItem("WishItems", JSON.stringify($scope.wishItems));
					$scope.WishLength += 1 ; 

				}
				



			}


			$scope.checked_cart = function(data)
			{
				var Wishlist_stored = JSON.parse(localStorage.getItem("WishItems"));

				
			

				for(var i = 0 ; i < Wishlist_stored.length ; i++)
				{
					
					if(Wishlist_stored[i].itemId[0] == data)
					{
						return true;
					}



				}

				return false;

				
			}

			$scope.limit = 5 ; 

			$scope.wishlist= function()
			{
				$scope.show_details = false;
				$scope.show_wishlist = true;
				$scope.products_show = false;
			}

			$scope.sort_order = function(data)
			{
				$scope.sortorder = data;
				$scope.ascsortorder = data;

			}

			$scope.rev_order=function(data)
			{
				if(data == "desc")
				{
					$scope.sortorder = "-"+$scope.sortorder;
					console.log($scope.sortorder);
				}
				else if(data == "asc")
				{
					$scope.sortorder = $scope.ascsortorder;

				}
			}


			$scope.showMore = function()
			{
				$scope.limit = $scope.similarproductdata.length;
			}

			$scope.showLess = function()
			{
				$scope.limit = 5;
			}

			$scope.editTitle = function(title)
			{

				var str = title.substring(0,35);

				var n = str.lastIndexOf(" ");

				var modified = str.substring(0,n+1) + "...";

				return modified ;

				

				

			}

			$scope.getMatches = function(searchText) {
    					return $http
      					.get("https://vanisingalaebayproductsearch.appspot.com/autocomplete?searchText" + searchText)
      					.then(function(data) {
        // Map the response object to the data object.

        console.log(data);
        return data;
      });
  };


  			$scope.reset = function()
  			{
  				$scope.Keyword = "";
  				$scope.Category = "default";
  				$scope.condition1 = false;
  				$scope.condition2 = false;
  				$scope.condition3 = false;
  				$scope.local = false;
  				$scope.free = false;
  				$scope.distance = "here";
  				$scope.distance_text = "";
  				$scope.distance_text_disabled = true ; 

  				$scope.show_details = false;

  				$scope.show_wishlist = false;

  				$scope.result_button = true;




  			}





	});






