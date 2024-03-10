# Unified Commodities Interface

## ROLES:
Admin, Supplier, Consumer, Transport Personnel

## Description:
Unified commodities interface is a (B2B) website aimed to facilitate large scale suppliers and consumer needs. It would act as a portal for suppliers to post their products and consumers to purchase products in bulk to satisfy requirements with access to transportation facilities.


## Use Cases:
### Supplier
    - List available products with images and descriptions. Quantity should be listed for each product and quality would reflect the rating of the supplier.
    - Personal mode of transport (if available)

### Consumer
    - Payment upon delivery
    - Personal pickup of goods (if available) / Job posting for transport by consumer

### Transport
    - Acceptance of job posting and authorization by either supplier/consumer depending on job posting


## Pages:
## Registration (Supplier/Consumer/Transporter)
    - Username text field (required)
    - Enter email and click continue go to otp verification page

## Otp verification page
    - Opt field (required)
    - Password text field (required)
    - Confirm Password text field (required)
    - Sign up button (Route to Profile page)
    - After otp verified it navigate to user detail page

    - Login link if already signed up

##	Login
    - Username text field (required)
    - Password text field (required)
    - Login button
    - Sign up link if not a member

##	Profile
    - Username text field (disabled - cannot edit)
    - Full Name text field (required)
    - Organization text field (required)
    - Email text field (required)
    - Phone number numbertext field (required)
    - Address (required)
    - Image (optional)
    - Update button -> redirect to home / landing page of user

    - Logout Button 
    - Rating (disabled only displayed for supplier and transporter)
    - Delivery count (disabled only for transporter)
		
## Subscription
    Subscription (for supplier and transporter) 
    (All supplier and transporter feature would depend on this - if not subscribed will redirect here)
    Monthly Rs.300 (supplier)
    Yearly Rs. 3000 (supplier)
    Monthly Rs.160 (transporter)
    Yearly Rs. 1800 (transporter)

## Supplier Pages:

## NavBar elements :
	My Products | Delivery Details | Profile
 
##	Product Listing / Editing page
    - Would contain list of supplier’s products
    - Add new product button  / Update / Delete button on hovering the product

    Add / Update form
    - Product Name (required)
    - Description (required , minimum 30 characters)
    - Price (required)
    - Quantity (required)
    - Image (required)
    - Type of Transportation (Self / Via transportation req / Consumer)(required)
    - Add / Update button

##	Delivery Status update for requested product
    - Contains list of product requests from consumer 
    - Update status to delivered to transporter button (Email notification)
    - Rate Transporter (optional)


## Consumer Pages:

## NavBar elements :
	Products | Transportation | Delivery Details | Profile

##	Buy Products by looking at available product listing
    - Contains list of products
    - Search to filter name of product based on search
    - On hover buy product button will appear
    - Buy Product form
    - All product fields from product listing
    - Quantity numbertext field (required)
    - Type of Transportation (Self / Via transportation req) [if supplier chooses consumer] (required)
    - Buy button  -> send request to supplier delivery of requested product 

##	Transportation request page
    - Contains list of requests made by user and status
    - Listing of transport request made in separate page
    - Accept or reject transportation request from transporter
    - On clicking the transportation request Transporter profile page is opened up with details about transporter.

##	Delivery Status update for requested product
    - Update status to delivery received button -> will store full transaction in admin finished transactions page (Email notification success/failure)
    - Rate Product and Transporter (optional)

## Transport Personnel:

## NavBar elements :
	Requests | Transport Proposals | Delivery Details | Profile

	Find Job posting for transport requirement
		- Contains list of transportation request for various products
		- On hover apply button will appear -> sends request to supplier / consumer
	Find out if request was approved or not
		- page with list of products to delivery with details and rejected requests (Email notification success/failure)

## Admin:

## NavBar elements :
    Home | Profile

## View all users, products, transport requests
	- List of all users (table - profile info)
	- List of all products  (table - products info)
	- List of all transportation requests and response  (table - transports info)
	- View of all Pages

## Finished transactions
	- List of all completed transactions  (table)
	- Supplier info
	- Consumer info
	- Transporter info
