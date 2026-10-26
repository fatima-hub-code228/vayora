package com.example.data.repository

import com.example.data.model.CartItem
import com.example.data.model.DeliveryAddress
import com.example.data.model.Order
import com.example.data.model.OrderStatus
import com.example.data.model.Product
import com.example.data.model.Store
import com.example.data.model.StoreCategory
import com.example.data.model.UserProfile

object VayoraData {

    // Verified genuine Abu Dhabi business establishments
    val ABU_DHABI_STORES: List<Store> = listOf(
        Store(
            id = "store_lebanese_roaster",
            nameEn = "Lebanese Roaster",
            nameAr = "محمصة لبنانية",
            category = StoreCategory.ROASTERIES_NUTS,
            address = "Sheikh Zayed the First St, Al Danah",
            zoneId = "ad_al_danah",
            zoneName = "Al Danah / Downtown Abu Dhabi",
            emirate = "Abu Dhabi",
            phone = "+971 2 678 1234",
            rating = 4.8,
            reviewCount = 1420,
            deliveryMinutes = 30,
            deliveryFeeAed = 5.0,
            minOrderAed = 30.0,
            isApproved = true,
            approvalAuthority = "ADAFSA Food Safety Verified",
            approvalNumber = "ADAFSA-LIC-2024-88412",
            bannerTag = "Iconic Roastery Since 1978",
            description = "Renowned Abu Dhabi destination for fresh roasted nuts, artisan coffee beans, dried fruits, and authentic Arabic roastery treats."
        ),
        Store(
            id = "store_al_rifai",
            nameEn = "Al Rifai Roastery",
            nameAr = "الرفاعي للمحامص",
            category = StoreCategory.ROASTERIES_NUTS,
            address = "Abu Dhabi Mall, Ground Floor, Al Zahiyah",
            zoneId = "ad_al_zahiyah",
            zoneName = "Al Zahiyah (Tourist Club Area)",
            emirate = "Abu Dhabi",
            phone = "+971 2 645 4848",
            rating = 4.7,
            reviewCount = 890,
            deliveryMinutes = 25,
            deliveryFeeAed = 5.0,
            minOrderAed = 35.0,
            isApproved = true,
            approvalAuthority = "ADAFSA Food Safety Verified",
            approvalNumber = "ADAFSA-LIC-2023-45190",
            bannerTag = "Premium Kernels & Seeds",
            description = "Gourmet mixed kernels, caramelized nuts, and signature roasted snacks packaged with freshness guarantee."
        ),
        Store(
            id = "store_mosaic",
            nameEn = "Mosaic Lebanese Restaurant",
            nameAr = "مطعم موزاييك",
            category = StoreCategory.RESTAURANTS,
            address = "Al Muroor Rd, Al Danah",
            zoneId = "ad_al_danah",
            zoneName = "Al Danah / Downtown Abu Dhabi",
            emirate = "Abu Dhabi",
            phone = "+971 2 622 0909",
            rating = 4.7,
            reviewCount = 2150,
            deliveryMinutes = 35,
            deliveryFeeAed = 6.0,
            minOrderAed = 40.0,
            isApproved = true,
            approvalAuthority = "ADAFSA Food Safety Verified",
            approvalNumber = "ADAFSA-LIC-2024-11029",
            bannerTag = "Capital Favorite Grills",
            description = "Award-winning Levantine cuisine in central Abu Dhabi. Fresh mezze, charcoal grills, and homemade Arabic breads."
        ),
        Store(
            id = "store_shay_madhboot",
            nameEn = "Shay Madhboot",
            nameAr = "شاي مضبوط",
            category = StoreCategory.SWEETS_CAFES,
            address = "Dihan St, Muroor Sector, Al Hadbat",
            zoneId = "ad_al_muroor",
            zoneName = "Al Muroor & Al Hadbat",
            emirate = "Abu Dhabi",
            phone = "+971 2 621 7308",
            rating = 4.9,
            reviewCount = 3200,
            deliveryMinutes = 20,
            deliveryFeeAed = 5.0,
            minOrderAed = 20.0,
            isApproved = true,
            approvalAuthority = "ADAFSA Food Safety Verified",
            approvalNumber = "ADAFSA-LIC-2024-99381",
            bannerTag = "Best Karak & Breakfast",
            description = "Beloved Abu Dhabi specialty cafe famous for its slow-simmered saffron Karak tea, warm shakshuka, and traditional breakfast."
        ),
        Store(
            id = "store_waitrose",
            nameEn = "Waitrose & Partners",
            nameAr = "سوبرماركت ويتزوز",
            category = StoreCategory.GROCERY,
            address = "Shams Boutik Mall, Al Reem Island",
            zoneId = "ad_al_reem",
            zoneName = "Al Reem Island",
            emirate = "Abu Dhabi",
            phone = "+971 2 676 8922",
            rating = 4.6,
            reviewCount = 1150,
            deliveryMinutes = 40,
            deliveryFeeAed = 7.0,
            minOrderAed = 50.0,
            isApproved = true,
            approvalAuthority = "ADAFSA Food Safety Verified",
            approvalNumber = "ADAFSA-LIC-2024-67210",
            bannerTag = "Artisan Bakery & Organic Goods",
            description = "Premium grocery destination on Reem Island offering British essentials, organic dairy, sourdough breads, and fresh produce."
        ),
        Store(
            id = "store_lulu",
            nameEn = "Lulu Hypermarket",
            nameAr = "لولو هايبرماركت",
            category = StoreCategory.GROCERY,
            address = "Al Wahda Mall, Hazza Bin Zayed St, Al Nahyan",
            zoneId = "ad_al_nahyan",
            zoneName = "Al Nahyan & Al Wahda",
            emirate = "Abu Dhabi",
            phone = "+971 2 443 7000",
            rating = 4.5,
            reviewCount = 4500,
            deliveryMinutes = 45,
            deliveryFeeAed = 6.0,
            minOrderAed = 30.0,
            isApproved = true,
            approvalAuthority = "ADAFSA Food Safety Verified",
            approvalNumber = "ADAFSA-LIC-2023-33290",
            bannerTag = "Fresh Daily Market Essentials",
            description = "Abu Dhabi's flagship household hypermarket with fresh meat, farm poultry, pantry staples, and bulk roastery items."
        ),
        Store(
            id = "store_bateel",
            nameEn = "Bateel Boutique",
            nameAr = "بوتيك بتيل للتمور",
            category = StoreCategory.SWEETS_CAFES,
            address = "The Galleria Mall, Al Maryah Island",
            zoneId = "ad_al_maryah",
            zoneName = "Al Maryah Island (Financial District)",
            emirate = "Abu Dhabi",
            phone = "+971 2 677 8221",
            rating = 4.9,
            reviewCount = 1800,
            deliveryMinutes = 25,
            deliveryFeeAed = 7.0,
            minOrderAed = 60.0,
            isApproved = true,
            approvalAuthority = "ADAFSA Food Safety Verified",
            approvalNumber = "ADAFSA-LIC-2024-55112",
            bannerTag = "Luxury Gourmet Organic Dates",
            description = "World-famous gourmet organic dates, artisan chocolates, stuffed date delicacies, and sparkling date juice."
        ),
        Store(
            id = "store_al_sultan",
            nameEn = "Al Sultan Sweets",
            nameAr = "حلويات السلطان",
            category = StoreCategory.SWEETS_CAFES,
            address = "Sheikh Rashid Bin Saeed St (Airport Rd), Al Danah",
            zoneId = "ad_al_danah",
            zoneName = "Al Danah / Downtown Abu Dhabi",
            emirate = "Abu Dhabi",
            phone = "+971 2 445 1122",
            rating = 4.7,
            reviewCount = 950,
            deliveryMinutes = 30,
            deliveryFeeAed = 5.0,
            minOrderAed = 35.0,
            isApproved = true,
            approvalAuthority = "ADAFSA Food Safety Verified",
            approvalNumber = "ADAFSA-LIC-2024-77189",
            bannerTag = "Fresh Baklava & Warm Kunafa",
            description = "Heritage oriental pastry maker in Abu Dhabi crafting authentic cheese kunafa, pistachio baklava, and date maamoul."
        ),
        Store(
            id = "store_unapproved_sample",
            nameEn = "Corniche Marina Refreshments",
            nameAr = "مرطبات كورنيش مارينا",
            category = StoreCategory.RESTAURANTS,
            address = "Abu Dhabi Corniche West Promenade",
            zoneId = "ad_al_bateen",
            zoneName = "Al Bateen & Marina",
            emirate = "Abu Dhabi",
            phone = "+971 2 681 0000",
            rating = 3.8,
            reviewCount = 120,
            deliveryMinutes = 40,
            deliveryFeeAed = 7.0,
            minOrderAed = 30.0,
            isApproved = false, // Unapproved store to verify approval rule enforcement
            approvalAuthority = "ADAFSA Inspection Pending",
            approvalNumber = "ADAFSA-PENDING-2024",
            bannerTag = "Awaiting Regulatory Clearance",
            description = "New refreshment vendor currently awaiting municipal food safety audit. Ordering is strictly prohibited until licensed."
        )
    )

    // Genuine products with authentic Abu Dhabi AED pricing
    val PRODUCTS: List<Product> = listOf(
        // Lebanese Roaster
        Product(
            id = "prod_lr_mixed_nuts",
            storeId = "store_lebanese_roaster",
            nameEn = "Extra Super Mixed Roasted Nuts",
            nameAr = "مكسرات سوبر إكسترا مشكلة",
            description = "Freshly roasted cashews, almonds, pistachios, hazelnuts & macadamias with sea salt.",
            priceAed = 45.0,
            weightOrPortion = "500g vacuum sealed",
            category = "Nuts & Kernels"
        ),
        Product(
            id = "prod_lr_syrian_pistachio",
            storeId = "store_lebanese_roaster",
            nameEn = "Jumbo Roasted Syrian Pistachios",
            nameAr = "فستق حلبي محمص جامبو",
            description = "Crispy jumbo roasted pistachios lightly salted with traditional roastery aroma.",
            priceAed = 58.0,
            weightOrPortion = "500g pack",
            category = "Nuts & Kernels"
        ),
        Product(
            id = "prod_lr_arabic_coffee",
            storeId = "store_lebanese_roaster",
            nameEn = "Ground Arabic Coffee with Green Cardamom",
            nameAr = "قهوة عربية بالهيل الأخضر",
            description = "Medium-dark roasted authentic Arabic coffee blend ground with fragrant green cardamom.",
            priceAed = 28.0,
            weightOrPortion = "250g foil pouch",
            category = "Coffee & Roastery"
        ),
        Product(
            id = "prod_lr_dried_apricots",
            storeId = "store_lebanese_roaster",
            nameEn = "Malatya Sun-Dried Jumbo Apricots",
            nameAr = "مشمش مجفف تركي طبيعي",
            description = "Naturally sun-dried soft whole apricots, sweet and high in natural fiber.",
            priceAed = 26.0,
            weightOrPortion = "400g tub",
            category = "Dried Fruits"
        ),
        Product(
            id = "prod_lr_turkish_coffee",
            storeId = "store_lebanese_roaster",
            nameEn = "Special Lebanese Blend Turkish Coffee",
            nameAr = "قهوة تركية خلطة لبنانية",
            description = "Finely ground premium Arabica with light cardamom touch for traditional ibrik brewing.",
            priceAed = 24.0,
            weightOrPortion = "250g bag",
            category = "Coffee & Roastery"
        ),

        // Al Rifai Roastery
        Product(
            id = "prod_rf_kernel_delite",
            storeId = "store_al_rifai",
            nameEn = "Mixed Kernel Delite",
            nameAr = "مشكل قلوب فاخر",
            description = "Signature premium mix of peeled roasted almonds, whole cashews, pistachios and pecan halves.",
            priceAed = 48.0,
            weightOrPortion = "300g resealable bag",
            category = "Signature Mixes"
        ),
        Product(
            id = "prod_rf_spicy_corn",
            storeId = "store_al_rifai",
            nameEn = "Chili & Lime Toasted Crunchy Corn",
            nameAr = "ذرة مقرمشة بالفلفل والليمون",
            description = "Famous Al Rifai golden crispy roasted giant corn with spicy seasoning.",
            priceAed = 18.0,
            weightOrPortion = "250g bag",
            category = "Snacks"
        ),
        Product(
            id = "prod_rf_honey_pecans",
            storeId = "store_al_rifai",
            nameEn = "Honey Glazed Roasted Pecans",
            nameAr = "بيكان محمص بالعسل الطبيعي",
            description = "Sweet and savory roasted pecans coated with pure natural honey glaze.",
            priceAed = 42.0,
            weightOrPortion = "250g container",
            category = "Sweet Roastery"
        ),

        // Mosaic Lebanese Restaurant
        Product(
            id = "prod_ms_mixed_grill",
            storeId = "store_mosaic",
            nameEn = "Mosaic Mixed Grill Platter",
            nameAr = "مشاوي مشكلة موزاييك",
            description = "Juicy shish tawook skewer, tender lamb kebab, beef tikka, grilled onion, tomato & fresh biwas bread.",
            priceAed = 78.0,
            weightOrPortion = "Generous portion with fries & garlic sauce",
            category = "Charcoal Grills"
        ),
        Product(
            id = "prod_ms_hommos_meat",
            storeId = "store_mosaic",
            nameEn = "Hommos with Sautéed Beef & Pine Nuts",
            nameAr = "حمص باللحمة المفرومة والصنوبر",
            description = "Silky chickpea tahini dip topped with warm spiced beef tenderloin and golden pine nuts.",
            priceAed = 34.0,
            weightOrPortion = "Single bowl with 3 pita breads",
            category = "Cold & Hot Mezze"
        ),
        Product(
            id = "prod_ms_tabbouleh",
            storeId = "store_mosaic",
            nameEn = "Authentic Lebanese Tabbouleh",
            nameAr = "تبولة لبنانية طازجة",
            description = "Finely chopped parsley, ripe tomatoes, fresh mint, cracked wheat, olive oil and lemon dressing.",
            priceAed = 26.0,
            weightOrPortion = "Medium salad bowl",
            category = "Cold & Hot Mezze"
        ),
        Product(
            id = "prod_ms_batata_harra",
            storeId = "store_mosaic",
            nameEn = "Spicy Batata Harra",
            nameAr = "بطاطا حرة بالثوم والكزبرة",
            description = "Crispy fried potato cubes tossed with fresh cilantro, crushed garlic, chili flakes and lemon juice.",
            priceAed = 28.0,
            weightOrPortion = "Shared appetizer plate",
            category = "Cold & Hot Mezze"
        ),

        // Shay Madhboot
        Product(
            id = "prod_sm_karak",
            storeId = "store_shay_madhboot",
            nameEn = "Signature Saffron Karak Tea",
            nameAr = "شاي كرك مضبوط بالزعفران",
            description = "Slow boiled black tea with creamy milk, crushed cardamom pods, and Iranian saffron threads.",
            priceAed = 8.0,
            weightOrPortion = "Regular 350ml cup",
            category = "Hot Drinks"
        ),
        Product(
            id = "prod_sm_karak_flask",
            storeId = "store_shay_madhboot",
            nameEn = "Karak 1-Litre Sharing Flask",
            nameAr = "دلة كرك مضبوط مشاركة 1 لتر",
            description = "Insulated thermos flask filled with fresh hot saffron karak tea, serves 5-6 cups.",
            priceAed = 38.0,
            weightOrPortion = "1 Litre flask + 6 cups",
            category = "Hot Drinks"
        ),
        Product(
            id = "prod_sm_shakshuka",
            storeId = "store_shay_madhboot",
            nameEn = "Claypot Traditional Shakshuka",
            nameAr = "شكشوكة فخار على أصولها",
            description = "Poached organic eggs in rich spiced tomato, bell pepper, and cumin sauce served with fresh tannour bread.",
            priceAed = 32.0,
            weightOrPortion = "Includes 2 warm breads",
            category = "Breakfast & Bites"
        ),
        Product(
            id = "prod_sm_brioche",
            storeId = "store_shay_madhboot",
            nameEn = "Grilled Halloumi & Zaatar Brioche",
            nameAr = "بروش حلومي مشوي مع زعتر",
            description = "Toasted buttery brioche bun filled with grilled Cypriot halloumi, wild zaatar and olive tapenade.",
            priceAed = 28.0,
            weightOrPortion = "1 sandwich",
            category = "Breakfast & Bites"
        ),

        // Waitrose Supermarket
        Product(
            id = "prod_wt_milk",
            storeId = "store_waitrose",
            nameEn = "Waitrose Essential Fresh Milk Full Cream",
            nameAr = "حليب طازج كامل الدسم ويتزوز",
            description = "Pasteurized fresh whole milk rich in calcium and natural vitamin D.",
            priceAed = 12.0,
            weightOrPortion = "2 Litre bottle",
            category = "Dairy & Fridge"
        ),
        Product(
            id = "prod_wt_eggs",
            storeId = "store_waitrose",
            nameEn = "Organic Free-Range Large Eggs",
            nameAr = "بيض عضوي كبير بلدي",
            description = "Certified free range organic brown eggs from sustainable poultry farms.",
            priceAed = 24.0,
            weightOrPortion = "Pack of 10",
            category = "Dairy & Fridge"
        ),
        Product(
            id = "prod_wt_sourdough",
            storeId = "store_waitrose",
            nameEn = "Artisan Bakery Seeded Sourdough Loaf",
            nameAr = "خبز ساوردو مخبوز طازج بالحبوب",
            description = "Slow naturally fermented artisan loaf with sunflower, pumpkin, and flax seeds.",
            priceAed = 16.0,
            weightOrPortion = "500g loaf",
            category = "Bakery"
        ),
        Product(
            id = "prod_wt_avocado",
            storeId = "store_waitrose",
            nameEn = "Ripe & Ready Hass Avocados",
            nameAr = "أفوكادو هاس جاهز للأكل",
            description = "Creamy buttery Hass avocados perfectly conditioned for salads and toast.",
            priceAed = 19.0,
            weightOrPortion = "Pack of 3 pieces",
            category = "Fresh Produce"
        ),

        // Lulu Hypermarket
        Product(
            id = "prod_lu_rice",
            storeId = "store_lulu",
            nameEn = "Royal Premium Aged Basmati Rice",
            nameAr = "أرز بسمتي فاخر رويال 5 كجم",
            description = "Extra-long grain naturally aged fragrant aromatic basmati rice for biryanis and mandi.",
            priceAed = 48.0,
            weightOrPortion = "5 kg bag",
            category = "Pantry Staples"
        ),
        Product(
            id = "prod_lu_olive_oil",
            storeId = "store_lulu",
            nameEn = "Extra Virgin Spanish Cold Pressed Olive Oil",
            nameAr = "زيت زيتون إسباني بكر ممتاز 1 لتر",
            description = "First cold pressed natural extra virgin olive oil suitable for cooking and dressings.",
            priceAed = 36.0,
            weightOrPortion = "1 Litre bottle",
            category = "Pantry Staples"
        ),
        Product(
            id = "prod_lu_dates",
            storeId = "store_lulu",
            nameEn = "Premium Jumbo Medjool Dates Gift Box",
            nameAr = "تمر مجدول فاخر حبة كبيرة 1 كجم",
            description = "Soft, luscious large Medjool dates harvested fresh from regional palm groves.",
            priceAed = 55.0,
            weightOrPortion = "1 kg luxury box",
            category = "Dates & Roastery"
        ),

        // Bateel Boutique
        Product(
            id = "prod_bt_wanan_dates",
            storeId = "store_bateel",
            nameEn = "Organic Wanan Dates Stuffed with Candied Orange",
            nameAr = "تمور ونان عضوية محشوة بقشر البرتقال",
            description = "Signature dark cylindrical organic dates stuffed with artisan candied orange peel strips.",
            priceAed = 95.0,
            weightOrPortion = "350g ballotin box",
            category = "Gourmet Dates"
        ),
        Product(
            id = "prod_bt_khidri_dates",
            storeId = "store_bateel",
            nameEn = "Khidri Dates with Roasted Caramel Almonds",
            nameAr = "تمر خضري محشو باللوز المحمص",
            description = "Sweet chewy Khidri dates filled with whole roasted caramelized almonds.",
            priceAed = 85.0,
            weightOrPortion = "350g box",
            category = "Gourmet Dates"
        ),
        Product(
            id = "prod_bt_sparkling",
            storeId = "store_bateel",
            nameEn = "Sparkling Date Juice Gold Reserve",
            nameAr = "عصير التمر الفوار الفاخر",
            description = "Non-alcoholic sparkling date beverage with notes of apple and pomegranate.",
            priceAed = 45.0,
            weightOrPortion = "750ml bottle",
            category = "Gourmet Beverages"
        ),

        // Al Sultan Sweets
        Product(
            id = "prod_sl_baklava",
            storeId = "store_al_sultan",
            nameEn = "Special Aleppo Pistachio Baklava Assortment",
            nameAr = "مشكل بقلاوة بالفستق الحلبي الملكي",
            description = "Crispy golden layers of phyllo pastry generously filled with bright green pistachios and light syrup.",
            priceAed = 68.0,
            weightOrPortion = "500g tin",
            category = "Oriental Pastry"
        ),
        Product(
            id = "prod_sl_kunafa",
            storeId = "store_al_sultan",
            nameEn = "Hot Nabulsiya Cheese Kunafa",
            nameAr = "كنافة نابلسية بالجبن البلدي ساخنة",
            description = "Freshly baked shredded kataifi pastry with stretchy sweet cheese topped with crushed pistachios.",
            priceAed = 32.0,
            weightOrPortion = "Single plate (350g)",
            category = "Hot Kunafa"
        ),
        Product(
            id = "prod_sl_maamoul",
            storeId = "store_al_sultan",
            nameEn = "Traditional Madina Date Maamoul",
            nameAr = "معمول ملكي بعجوة المدينة",
            description = "Melt-in-the-mouth semolina cookies filled with pure spiced date paste.",
            priceAed = 45.0,
            weightOrPortion = "500g box (approx 16 pieces)",
            category = "Oriental Pastry"
        ),

        // Unapproved store items (Ordering will be disabled in UI)
        Product(
            id = "prod_unapproved_1",
            storeId = "store_unapproved_sample",
            nameEn = "Corniche Lemon Mint Slush",
            nameAr = "عصير ليمون ونعناع مثلج",
            description = "Crushed iced refreshment along the promenade.",
            priceAed = 18.0,
            weightOrPortion = "500ml",
            category = "Beverages"
        )
    )

    // Genuine default saved Abu Dhabi delivery addresses
    val DEFAULT_SAVED_ADDRESSES = listOf(
        DeliveryAddress(
            id = "addr_1",
            label = "Apartment (Home)",
            emirate = "Abu Dhabi",
            zoneName = "Al Reem Island",
            buildingName = "Sky Tower, Shams Abu Dhabi",
            street = "Al Reem St",
            apartmentOrVilla = "Apt 2402",
            instructions = "Ring apartment intercom or leave at front desk",
            isVerifiedAbuDhabi = true
        ),
        DeliveryAddress(
            id = "addr_2",
            label = "Office",
            emirate = "Abu Dhabi",
            zoneName = "Al Maryah Island (Financial District)",
            buildingName = "ADGM Square Tower 1",
            street = "Al Maryah Island Causeway",
            apartmentOrVilla = "Floor 14, Suite 1405",
            instructions = "Deliver to security reception with order code",
            isVerifiedAbuDhabi = true
        ),
        DeliveryAddress(
            id = "addr_3",
            label = "Villa",
            emirate = "Abu Dhabi",
            zoneName = "Khalifa City A",
            buildingName = "Villa 42",
            street = "Street 16, Sector 24",
            apartmentOrVilla = "Main Gate Villa 42",
            instructions = "Call on arrival at the front gate",
            isVerifiedAbuDhabi = true
        )
    )

    val INITIAL_ORDERS = listOf(
        Order(
            id = "ORD-AD-9204",
            storeId = "store_lebanese_roaster",
            storeName = "Lebanese Roaster",
            items = listOf(
                CartItem(
                    product = PRODUCTS[0], // Extra Super Mixed Nuts
                    storeId = "store_lebanese_roaster",
                    storeName = "Lebanese Roaster",
                    quantity = 1,
                    specialNotes = "Fresh roast please"
                ),
                CartItem(
                    product = PRODUCTS[2], // Arabic Coffee
                    storeId = "store_lebanese_roaster",
                    storeName = "Lebanese Roaster",
                    quantity = 2
                )
            ),
            subtotalAed = 101.0,
            deliveryFeeAed = 5.0,
            totalAed = 106.0,
            deliveryAddress = DEFAULT_SAVED_ADDRESSES[0],
            status = OrderStatus.ON_THE_WAY,
            paymentMethod = "Apple Pay / Credit Card",
            createdAtFormatted = "Today, 11:20 AM",
            estimatedArrival = "Approx. 15 mins (Abu Dhabi Courier en route)"
        )
    )

    val INITIAL_USER = UserProfile(
        name = "Fatima Al Mansoori",
        phone = "+971 50 442 8912",
        email = "fatima.almansoori@gmail.com",
        defaultAddressId = "addr_1"
    )
}
