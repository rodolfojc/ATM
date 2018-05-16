import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class AtmProg {
    
    String userAccInput="";
    String userAccInputWithFormat="";
    String finalValidAcc="";
    String pinWithFormat="";
    String userPinInput="";
    String[] Data=null; 
    String[] typeData100=null;
    String[] typeData200=null;
    String[] typeData300=null;
    String[] typeDataMore301=null;
    int[] counterSummary=null;
    int [] referenceTypesAccounts=null;
    String[] numbersAccs=null;   
    int numFiles=0;
    String[][] accounts=null;
    String[] accountsBalance=null;
    double[] converAccounts=null;
    double moneyBank=0;
    
    
//////////////////////////////////////////////////////////////////////////
//DEFAULT CONSTRUCTOR                                                   //
//////////////////////////////////////////////////////////////////////////
	
    public AtmProg() {       
	    welcomeMessage(); //WELCOME MESSAGE TO THE ATM
	    accMessage(); // MESSAGE TO REQUEST AN ACCOUNT NUMBER
		userAccInput= readInput(); // VARIABLE WHICH CALL THE METHOD THAT CATCH AN USER ENTRY, RETURN OF THE MOTHOD. 
		userAccInputWithFormat= AccValidation(userAccInput); // VARIBLE WHITH TAKE A RETURN THAT IT IS A VALID FORMAT 8 DIGITS ACCOUNT
		finalValidAcc=accValidationSys(userAccInputWithFormat); // VARIBLE WHICH TAKE A RETURN OF A VALID ACCOUNT IN TXT FILES 
		userPinInput=messaInputPin(); // METHOD THAT SHOW A MESSAGE TO REQUEST A PIN NUMBER AND RETUN IT. 
		pinWithFormat=userPinFormatValidation(userPinInput); // VARIBLE WHICH TAKE A RETURN OF A VALID PIN FORMAT 4 DIGITS 
		menu(); // MENU OPCION, AT THIS STAGE THE PIN NUMBER IS NOT VALIDATED, IT TAKES PLACES AT THE MOMENT THAT THE USER TRY TO DO A TRANSACTION
								
		
	}
	
//////////////////////////////////////////////////////////////////////////
//MAIN METHOD                                                           //
//////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		new AtmProg();

	}
    
//////////////////////////////////////////////////////////////////////////
//WELCOME MESSAGE
//////////////////////////////////////////////////////////////////////////
	public void welcomeMessage() {
		System.out.println("Welcome to ATM"); // SIMPLE PRINT MASSAGE IN THE SCREEN 
	}
	
//////////////////////////////////////////////////////////////////////////
//ACCOUNT MESSAGE 
//////////////////////////////////////////////////////////////////////////
	public void accMessage() {
		System.out.println("Please, enter your EIGHT(8) digits of your Account Number");
	}
	
		
//////////////////////////////////////////////////////////////////////////
//INPUT(S) READER                                                       //
//////////////////////////////////////////////////////////////////////////
	
	//METHOD THAT USE BUFFEREDREADER TO CATCH A STRING TYPE ENTRY, RETURN STRING
	public String readInput() {   
		String input="";
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		try {
		input=br.readLine();
		}catch (Exception e) {System.out.println("Something wrong with the input!!!");}
		return input;
	}
	
//////////////////////////////////////////////////////////////////////////
//ACCOUNT FORMAT VALIDATION (8 DIGITS, AND CHARACTERS BETWEEN 0-9)      //
//////////////////////////////////////////////////////////////////////////
	
	public String AccValidation(String originalUserAccInput) { 
		String userAccFormat=""; // LOCAL VARIABLE TO USE AS A RETURN
		
		boolean flag=false; // FLAG FOR THE LOOP 
		    // "IF" STATEMENT WHICH VALIDATE 8 DIGITS LENGTH, AND ONLY NUMBER 
		    if ((originalUserAccInput.length()>=8) && (originalUserAccInput.length()<9)  && (originalUserAccInput.matches("[0-9]+"))) {
			userAccFormat=originalUserAccInput;
			return userAccFormat; // RETURN, IT IS VALID
			} else {
			do {  // DO WHILE LOOP - UNTIL A VALID ENTRY IS INTROUCED  
			System.out.println("The account does not have a valid format, please try again!");
			userAccInput=readInput(); // REQUEST A NEW ENTRY TO VALIDATE
			if ((userAccInput.length()>=8) && (userAccInput.length()<9)  && (userAccInput.matches("[0-9]+"))) {
				flag=true;
			} else {
			flag=false;
		    }
			}while (flag==false);
			userAccFormat=userAccInput; 
			return userAccFormat; // RETURN, IT IS VALID
			}
	}
	
////////////////////////////////////////////////////////////////////////////
//VALIDATION OF THE ACCOUNT, CHECKING THAT MATCH WITH THE LOADED FILE TXT //
////////////////////////////////////////////////////////////////////////////
		
	 public String accValidationSys(String validAccFormat) {   
			String userAccInputFinal=""; //LOCAL VARIABLE TO USE AS A RETURN
			
			readFile(validAccFormat); // CALL THE METHOD THAT READ THE FILE TO THEN COMPARE  
			if (validAccFormat.equals(Data[0])) { // IF STATEMENT TO COMPARE ENTRY AND FILE DATA
			userAccInputFinal=validAccFormat;
			return userAccInputFinal; // RETURN THE ENTRY MATCH WITH THE FILE DATA
			}
			else {
			boolean flag=false;
			do {  // DO WHILE LOOP - UNTIL A VALID ENTRY IS INTROUCED 
				System.out.println("The account does not exist, please try again");
				userAccInput= readInput(); // REQUEST A NEW ENTRY
				userAccInputWithFormat= AccValidation(userAccInput); // VALIDATE THE NEW ENTRY
				readFile(userAccInputWithFormat); // LOAD THE FILE ACCORDING TO THE NEW ENTRY 
				if (userAccInputWithFormat.equals(Data[0])) { // IF STATEMENT TO COMPARE ENTRY AND FILE DATA
					flag=true;
					userAccInputFinal=userAccInputWithFormat;
					return userAccInputFinal; // RETURN THE NEW ENTRY IF MATCH WITH THE FILE DATA
				} else {
				flag=false;
		    	}
						
			   }while (flag==false);
			}
			return userAccInputFinal; // RETURN THE ENTRY MATCH WITH THE FILE DATA
	}
	
	

//////////////////////////////////////////////////////////////////
//      LOAD THE ACCOUNT FILE WITH THE VALID ACC NUMBER         //
//////////////////////////////////////////////////////////////////
	 
	public void readFile(String ValidAcc) {
		
		Data= new String [3]; // ARRAY STRING WITH 3 SPACES TO STORE ACCOUNT, PIN AN BALANCE
		
		String folderLocation=""; // VARIABLE TO STORE THE CURRENT LOCATION
		folderLocation=currentDirectory(); // CALL METHOD WHICH RETURN IS THE CURRENT LOCATION
		
		try {
		//USING THE VARIABLES, FOLDERLOCATION AND THE VALIDACC, IT IS POSSIBLE TO FIND THE TXT TO LOAD
		BufferedReader br = new BufferedReader(new FileReader(folderLocation+ValidAcc+".txt"));
	
		  for(int i=0; i<3; i++){ // LOOP FOR - TO LOAD THE DATA OF 3 LINES 
		  Data[i]=br.readLine();
		  }
		  br.close(); //CLOSE THE FILE 
		  
							
		}catch (Exception e) {System.out.println("System Error!! Try again!! ");}
				
	}

/////////////////////////////////////////////////////////////////////////////////
//SIMPLE PIN NUMBER REQUEST WITH MESSAGE                                       //
/////////////////////////////////////////////////////////////////////////////////
	
	public String messaInputPin() {
		
		System.out.println("Please, enter your four(4) digits PIN number: ");
		String pinInput=readInput();
		return pinInput;
	}

/////////////////////////////////////////////////////////////////////////////////
//PIN NUMBER FORMAT VALIDATION                                                 //
/////////////////////////////////////////////////////////////////////////////////
	
	
	public String userPinFormatValidation(String originalUserPinInput) {
		String validPinFormat=""; // LOCAL VARIABLE TO USE AS A RETURN
		
		boolean flag=false;
		// IF STATEMENT TO VALIDATE 4 DIGITS ENTRY AND ONLY NUMBER
		if ((originalUserPinInput.length()>=4) && (originalUserPinInput.length()<5)  && (originalUserPinInput.matches("[0-9]+"))) {
			validPinFormat=originalUserPinInput;
			return validPinFormat; // RETURN, IT IS VALID
			} else {
			do {
			System.out.println("The PIN number does not have a valid format, please try again!");
			userPinInput=readInput(); // REQUEST A NEW ENTRY TO VALIDATE
			if ((userPinInput.length()>=4) && (userPinInput.length()<5)  && (userPinInput.matches("[0-9]+"))) {
				validPinFormat=userPinInput; // RETURN, IT IS VALID
				flag=true;
			} else {
				flag=false;
		           }
			}while (flag==false);
			return validPinFormat; // RETURN, IT IS VALID
			}
		}
	
//////////////////////////////////////////////////////////////////////////////////
//MENU METHOD WHICH SHOWS THE OPTIONS AND REQUEST A  VALID ENTRY                //
//////////////////////////////////////////////////////////////////////////////////	

		public void menu() {
			boolean flag=false;
			do {
			System.out.println("Please enter an option (1-6)");
			System.out.println("(1) Check the current Account Balance");
			System.out.println("(2) Withdraw money");
			System.out.println("(3) Change the PIN");
			System.out.println("(4) Stock prices of the Bank");
			System.out.println("(5) Logout");
			System.out.println("(6) Bank Summary (ADMIN)");
			
			String MenuInput=readInput(); // REQUEST A ENTRY 
			// IF STATEMENT TO VALIDATE OPTIONS BETWEEN 1-6
			if ((MenuInput.length()>=1) && (MenuInput.length()<7)  && (MenuInput.matches("[0-9]+"))) {
			    flag=true;
				   if (MenuInput.equals("1")) {
				 balance(); // CALL BALANCE METHOD 
			} else if (MenuInput.equals("2")) {
				 withdraw(); // CALL WITHDRAW METHOD
			} else if (MenuInput.equals("3")) {
				 changePIN(); // CALL CHANGE METHOD 
			} else if (MenuInput.equals("4")) {
				listOfPrices(); // CALL LISTOFPRICES METHOD TO SHOW THE STOCK PRICES
			} else if (MenuInput.equals("5")) {
				logout(); // CALL LOGOUT METHOD 
			} else if (MenuInput.equals("6")) {
				bankSummary(); // CALL BANKSUMARRY 
			}
			else {
				System.out.println("The option is not valid, try again");	
				flag=false;
				menu(); // CALL MENU METHOD 
				
			  }
			}
			}while (flag==false);
		}
		
		
//////////////////////////////////////////////////////////////////////////////////
//PIN VAIDATION, IF FAIL, THE ALL PROGRAM STAR                                  //
//////////////////////////////////////////////////////////////////////////////////

		// THIS METHOD IS CALLED WHEN AN OPTION IS ENTRY IN THE MAIN MENU
		// THE PIN VALIDATION TAKE PLACE AT THE MOMENT THE USER TRY TO DO A TRANSACCION
		
		public boolean pinValidationSys(String pinWithFormat) {
			boolean valid=false;
			
			// IF STATEMENT TO COMPARE THE PIN INTRODUCED AND THE PIN ON THE FILE DATA
			if(pinWithFormat.equals(Data[1])) {
				valid=true;
			} else {
				valid=false;
				noValidPinMessage(); // NO VALID PIN, IT DID NOT MATCH WITH THE FILE DATA
				new AtmProg(); // RESTART THE PROGRAM 
			}
			return valid;
			
		}
		
		
//////////////////////////////////////////////////////////////////////////////////
//BALANCE METHOD, IT SHOWS JUST THE CURRENT BALANCE                             //
//////////////////////////////////////////////////////////////////////////////////
		
		
		public void balance() {
			
       			
			pinValidationSys(pinWithFormat); // PIN VALIDATION, IF FAIL, THE PROGRAM RESTART 
			
			double ConvertDATA2= Double.valueOf((Data[2])); // BALANCE DATA TO DOUBLE
			double RoundCoverDATA2=Math.round(ConvertDATA2); // ROUND THE DATA 
			System.out.println("Your current balance is: "+RoundCoverDATA2); // PRINT THE DATA WITH 1 DECIMAL 
			System.out.println();
			logout();
	
		}

//////////////////////////////////////////////////////////////////////////////////
//WITHDRAW METHOD                                                               //
//////////////////////////////////////////////////////////////////////////////////
		
		public void withdraw() {
			int a=10;
			int b=20;
			int c=50;
			int d=100;
			double newBalanceDou=0;
			String newBalance="";
			double cal=0;
			double douBalance=Double.valueOf(Data[2]); // BALANCE DATA TYPE STRING TO DOUBLE
			
			withdrawMenu(); // IT SHOWS OPTION OF 10, 20, 50, 100 EUR. AND OTHER AMOUT OPTION
			String optWithdraw=readInput(); // REQUEST AN ENTRY 
		
			
		    String stringAmount="";
	        double doubleAmount=0;
			
			//IF STATEMNT TO ASSIGN A VALUE 
			if(optWithdraw.equals("1")) {
				cal=a; // 10 EUR
			} else if(optWithdraw.equals("2")) {
				cal=b; // 20 EUR 
			} else if(optWithdraw.equals("3")) {
				cal=c; // 50 EUR
			} else if(optWithdraw.equals("4")) {
				cal=d; // 100 EUR
			} else if(optWithdraw.equals("5")) { // OTHER AMOUNT 
				
				pinValidationSys(pinWithFormat); // PIN VALIDATION
				System.out.println("Please, enter the amount do you want to withdraw (Up to 500 EUR): ");
				stringAmount=readInput(); // REQUEST AMOUNT 
				boolean flag=false;
				do {
					// IF STAMENT TO VALIDATE THE ENTRY, IF IT IS BETWEEN 3 DIGIST AND ONLY NUMBER
					if ((stringAmount.length()>=1) && (stringAmount.length()<4)  && (stringAmount.matches("[0-9]+"))) {
					    flag=true;
					    doubleAmount=Double.valueOf(stringAmount); // IF IS IT VALID, THE STRING DATA IS CONVERTED TO DOUBLE (NEW VARIBLE) 
					}
				    else {
					    System.out.println("The entry is not valid, please try again");	
						flag=false;
						withdraw(); // CALL THE METHOD AGAIN TO GO BACK 
				    }
				   }while(flag==false);
				
				//IF STATEMENT TO VALIDATE THAT THE AMOUNT IS MORE THAT 10 EUR (NOTE), MULTIPLES OF 10 (NOTE) AND LESS THAN THE BALANCE
				if((doubleAmount>=10) && (doubleAmount<=500) && (doubleAmount%10==0) && (doubleAmount<=douBalance) ){
	        		cal=douBalance-doubleAmount; // NEW BALANCE
	        		takeMoneyAndNewBalace(cal); // MESSAGE WITH NEW BALANCE
					newBalance=String.valueOf(cal); // NEW BALANCE IN DOUBLE TO STRING
					Data[2]=newBalance; // UPDATE THE VARIABLE WITH NEW BALANCE
					upDateTxtFile(); // UPDATE THE TXT FILE DATA
					System.out.println();
					logout();
			       }
	        	else {
	        	    // IF THIS IS RUN, THEN THE USER DOES NOT HAVE MONEY
	        		if((doubleAmount>=10) && (doubleAmount<=500) && (doubleAmount%10==0)) {
	        	    	noMoney(); // METHOD WHICH SHOWS A MESSAGE "NO MONEY" 
	        	    	System.out.println();
	        	    	logout();
	        	    //IF THE USER HAVE MONEY, THEN IT IS A PROBLEM WITH THE ENTRY MULTIPLES AND LIMITS	
	        	    } else {
	        	    	System.out.println("The amount is not valid, it must be multiple of 10 EUR and up to 500 EUR, please try again");
	        	   		withdraw();
	        	    }
	        	}
			}
			// ANY OTHER ENTRY NOT VALID IN THE MENU BETWEEN 1 - 5 
			 else {
			   System.out.println("Option not valid, please try again!");
			   withdraw();
			}
			
			pinValidationSys(pinWithFormat); // PIN VALIDATION
			
			if (cal<=douBalance) {
				newBalanceDou=douBalance-cal; // NEW BALANCE
				takeMoneyAndNewBalace(newBalanceDou); // NEW BALANCE MESSAGE
				newBalance=String.valueOf(newBalanceDou); // NEW BALANCE IN DOUBLE TO STRING
				Data[2]=newBalance; // UPDATE THE VARIABLE WITH NEW BALANCE 
				upDateTxtFile(); // UPDATE THE TXT FILE DATA
				System.out.println();
				logout();
			} else if(cal>douBalance) {
				noMoney();
				System.out.println();
				logout();
			} 
		}
			
//////////////////////////////////////////////////////////////////////////////////
//METHOD TO CHANGE THE PIN NUMBER                                               //
//////////////////////////////////////////////////////////////////////////////////
		  		
		
		public void changePIN() {
			
			String newPIN="";
			String newPIN2="";
						
			pinValidationSys(pinWithFormat);
			
			System.out.println("Please, enter your new Four(4) digits PIN Number");
			newPIN=readInput(); // NEW PIN
			String ValNewPIN=userPinFormatValidation(newPIN);
			System.out.println("Please, enter again your new Four(4) digits PIN Number");
			newPIN2=readInput(); // CONFIRM PIN
			String ValNewPIN2=userPinFormatValidation(newPIN2);
			
			// IF STATEMENT TO COMPARE IF THE NEW PIN NUMBER AND THE CONFIRMATION ARE THE SAME 
			if (ValNewPIN.equals(ValNewPIN2)) {
			   	pinWithFormat=newPIN; // UPDATE THE VARIABLE THAT IT IS USED TO VALIDATE THE PIN IN DIFFERENT SECTIONS
			   	Data[1]=newPIN; // UPDATE THE VARIABLE WITH NEW PIN NUMBER 
			   	upDateTxtFile(); // UPDATE THE TXT FILE DATA 
			   	System.out.println("Your PIN has been changed succesful... Thanks you");
			   	System.out.println();
			   	logout();
			}
			else {
			   	System.out.println("The PIN(S)  did not match, please start again ");
			   	new AtmProg(); // RESTART THE PROGRAM
			    }
			} 
			
			
			
			
//////////////////////////////////////////////////////////////////////////////////
//LOGOUT METHOD                                                                 //
//////////////////////////////////////////////////////////////////////////////////
		
		public void logout() {
			String yesOrNoInput="";
			boolean flag=false;
			do {
				System.out.println("Would you like to do another transaction (Y/N)");
				yesOrNoInput=readInput(); // REQUEST AN ANSWER
				yesOrNoInput=yesOrNoInput.toUpperCase();
				// IF STATEMENT WHICH JUST EVALUATE UPPERCASE ENTRY FOR 4 VALID OPTIONS
				if(yesOrNoInput.equals("Y") || yesOrNoInput.equals("YES") || yesOrNoInput.equals("N") || yesOrNoInput.equals("NO") ) {
					flag=true;
				}
				else { // NO VALID 
					System.out.println("The entry is not correct, please try again\n");
					flag=false;
				}
					
				}while(flag==false); 
			if (yesOrNoInput.equals("Y") || yesOrNoInput.equals("YES")) {
				menu(); // CALL METHOD MENU 
			}
			else {
				upDateTxtFile(); // FINAL UPDATE FOR TXT FILE DATA BEFORE FINISH THE PROGRAM
				new AtmProg(); // START THE PROGRAM AGAIN
				
			}
		}
		
//////////////////////////////////////////////////////////////////////////////////
//WITHDRAW OPTIONS MESSAGE                                                      //
//////////////////////////////////////////////////////////////////////////////////
		
		public void withdrawMenu () {
			System.out.println("Please, select the amount to withdraw");
			System.out.println("(1) 10 EUR");
			System.out.println("(2) 20 EUR");
			System.out.println("(3) 50 EUR");
			System.out.println("(4) 100 EUR");
			System.out.println("(5) Other (Up to 500 EUR)");
		}

//////////////////////////////////////////////////////////////////////////////////
//METHOD THAT UPDATE THE THREE VARIABLE ON THE TXT FILE                         //
//////////////////////////////////////////////////////////////////////////////////
				
		public void upDateTxtFile() {
			String folderLocation="";
			folderLocation=currentDirectory();
			
			try {
			BufferedWriter bw= new BufferedWriter (new FileWriter (folderLocation+finalValidAcc+".txt") ); 
			for(int i=0; i<3; i++) {
				bw.write(Data[i]); 
				bw.newLine();
			}
			bw.close();
			}catch (Exception e) {}
		}
		
//////////////////////////////////////////////////////////////////////////////////
//METHOD TO SHOW A MESSAGE AND NEW BALANCE, TO BE USE IN WITHDRAW METHOD        //
//////////////////////////////////////////////////////////////////////////////////
			
		public void takeMoneyAndNewBalace(double cal) {
			
			double roundCal=Math.round(cal); // BALANCE WITH ONE DECIMAL PLACE
			System.out.println("Please, take your money! Thanks you");
			System.out.println("Your new balance is "+roundCal);
		}
		
//////////////////////////////////////////////////////////////////////////////////
//METHOD TO SHOW A MESSAGE AND NEW BALANCE, TO BE USE IN WITHDRAW METHOD        //
//////////////////////////////////////////////////////////////////////////////////
		
		public void noMoney() {
			System.out.println("You do not have enogth money! Thanks you");
		}

//////////////////////////////////////////////////////////////////////////////////
//METHOD TO SHOW A MESSAGE WHEN THE PIN IS NOT VALID                            //
//////////////////////////////////////////////////////////////////////////////////
		
		public void noValidPinMessage() {
			System.out.println("The operation can not continue, PIN is not corret, try again later ");
		}

//////////////////////////////////////////////////////////////////////////////////
//METHOD BANK SUMARRY  - EXTRA                                                  //
//////////////////////////////////////////////////////////////////////////////////
		
       public void bankSummary () {
    	   String AdminValidation="";
    	   String folderLocation="";
   		  
    	   folderLocation=currentDirectory(); // GET THE CURRENT WORK LOCATION
    	   
    	   System.out.println("PLEASE, ENTER MASTER CODE"); // MASTER VALIDATION
    	   AdminValidation=readInput();
    	   
    	   if (AdminValidation.equals("admin")) {
    		
    		File folder = new File(folderLocation); // LIST THE FILES AT "ACCOUNTS" FOLDER
    		   
    		File[] listOfFiles = folder.listFiles(); // ARRAY WITH INFORMATION LISTED 
    	 		
    		accounts = new String[listOfFiles.length][]; // NEW ARRAY WHICH LENGTH IS THE NUMBER OF FILES LISTED

    		for(int i=0;i<listOfFiles.length;i++) // FOR LOOP TO CREATE AN ARRAY STRING WITH THE NUMBER OF ACOOUNTS WITH 3 SUBARRAYS 
    		{
    			accounts[i] = new String[3];
    		}
    		
      		for(int i=0; i<listOfFiles.length; i++) { // FOR LOOP TO READ THE INFOMATION OF THE ACCOUNTS AND THE 3 LINES OF INFORMATION
    		try {
    		   BufferedReader br= new BufferedReader(new FileReader (listOfFiles[i]));
    		   for(int f=0; f<3; f++) {
    		   accounts[i][f]=br.readLine();}
    		   br.close();
    		   }catch (Exception e) {System.out.println("No file");}  
    		}
    		
      		numFiles=listOfFiles.length;
    		
    		accountsBalance= new String[numFiles]; // NEW ARRAY TO SAVE JUST THE INFORMATION OF THE BALANCE OF ALL ACCOUNTS (STRING)
    		for(int i=0; i<numFiles; i++) {
    		   accountsBalance[i]=accounts[i][2];
    		   }
    		
    		converAccounts=new double[numFiles]; // NEW ARRAY TO CONVERT THE BALANCES TYPE STRING TO DOUBLE 
    		for(int i=0; i<numFiles; i++) {
    		   converAccounts[i]=Double.valueOf(accounts[i][2]);
    		   }
    		
    		for(int i=0; i<numFiles; i++) { // ADDITION OF THE TOTAL BALANCES OF ALL THE ACCOUNTS 
    			moneyBank= moneyBank+converAccounts[i];
    			}
    		
    		System.out.println("Total Accounts (accounts data folder): "+numFiles);
    		System.out.println("Total Money in Accounts: EUR "+moneyBank);
    		System.out.println();
    		typeOfAccount(converAccounts);
    	    summaryAccResult();
    	    System.out.println();
    	    logout();
    	   }
    	   else {
    		   System.out.println("!!!ACCCES DENIED!!!"); // IF THE MASTER CODE IS NOT CORRECT
    		   new AtmProg();
    	   }
    	   
    	   }
//////////////////////////////////////////////////////////////////////////////////
//METHOD TO DIVIDE THE ACCOUNTS BY CATEGORIES                                   //
//////////////////////////////////////////////////////////////////////////////////
              
              
      public void typeOfAccount(double[] converAccounts) {
    	  typeData100= new String[numFiles]; // ACCOUNTS BETWEEN 0 AND 100 
    	  typeData200= new String[numFiles]; // ACCOUNTS BETWEEN 101 AND 200 
    	  typeData300= new String[numFiles]; // ACCOUNTS BETWEEN 201 AND 300  
    	  typeDataMore301= new String[numFiles];  // ACCOUNTS WITH MORE THEN 301 
    	  counterSummary= new int[numFiles]; // COUNTER FOR LOOPS
    	  referenceTypesAccounts= new int[numFiles]; // REFERENCE FOR LOOPS
    	  numbersAccs= new String[numFiles]; // COUNTER FOR LOOPS
    	  
    	  for(int i=0; i<numFiles; i++) {
    		  numbersAccs[i]=accounts[i][0]; // LOOP TO SAVE IN A ARRAY THE NUMBER OF THE ACCOUNTS 
    	  }
    	  
    	 	  
    	  for(int i=0; i<numFiles;i++) {
    	  if(converAccounts[i]<=100) { // CONDITION - BALANCE 
    		typeData100[i]=numbersAccs[i]; // SAVE THE NUMBER OF THE ACCOUNT
    		counterSummary[0]++; // ACOUNTER THAT INCREASE WHEN AN ACCOUNT IS IN THIS CONDITION
    		referenceTypesAccounts[i]=0; // REFERENCE TO COMPARE WHICH ARE THE ACCOUNTS IN THIS CATEGORY
    	  //SAME FOR THE REST OF THE CONDITIONS
    	  } else if(converAccounts[i]>=101 && converAccounts[i]<=200 ) {
    		typeData200[i]=numbersAccs[i];
    		counterSummary[1]++;
    		referenceTypesAccounts[i]=1;
    	  } else if(converAccounts[i]>=201 && converAccounts[i]<=300 ) {
    		typeData300[i]=numbersAccs[i];
    		counterSummary[2]++;
    		referenceTypesAccounts[i]=2;
    	  }else if(converAccounts[i]>=301) {
    		typeDataMore301[i]=numbersAccs[i];
    		counterSummary[3]++;;
    		referenceTypesAccounts[i]=3;
    		
    	  }
    	  }
    	  
      }
 
//////////////////////////////////////////////////////////////////////////////////
//METHOD TO SHOW THE RESULTS OF THE METHOD TYPE OF ACCOUNTS                     //
//////////////////////////////////////////////////////////////////////////////////
      
      public void summaryAccResult() {
    	  System.out.println("Small Account(s) (up to EUR 100): " +counterSummary[0]);
    	  System.out.println("Account(s):  ");
    	  for(int i=0;i<numFiles;i++) {
    		  if(referenceTypesAccounts[i]==0) { 
    		  System.out.println(typeData100[i]+" : EUR "+converAccounts[i]);	  
    		  }
    	  }
    	  System.out.println();
    	  
    	  System.out.println("Medium Accounts (up to EUR 200): " +counterSummary[1]);
    	  System.out.println("Account(s):  ");
    	  for(int i=0;i<numFiles;i++) {
    		  if(referenceTypesAccounts[i]==1) {
    		  System.out.println(typeData200[i]+" : EUR "+converAccounts[i]);
    		  }
    	  }
    	  System.out.println();
    	  
    	  System.out.println("Large Accounts (up to EUR 300): " +counterSummary[2]);
    	  System.out.println("Account(s):  ");
    	  for(int i=0;i<numFiles;i++) {
    		  if(referenceTypesAccounts[i]==2) {
    		  System.out.println(typeData300[i]+" : EUR "+converAccounts[i]);
    		  }
    	  }
    	  System.out.println();
    	  
    	  System.out.println("Extra Large Accounts (more than EUR 300):" +counterSummary[3]);
    	  System.out.println("Account(s):  ");
    	  for(int i=0;i<numFiles;i++) {
    		  if(referenceTypesAccounts[i]==3) {
    		  System.out.println(typeDataMore301[i]+" : EUR "+converAccounts[i]);
    		  }
    	  }
    	  
      }
    	         
//////////////////////////////////////////////////////////////////////////////////
//METHOD TO SHOW THE BANK STOCK PRICES                                          //
////////////////////////////////////////////////////////////////////////////////// 
      
      public void listOfPrices() {
    	  String[] list= new String[100];
    	  
    	  pinValidationSys(pinWithFormat);
    	  
    	  String folderLocation="";
    	  folderLocation=System.getProperty("user.dir");
    	     	  
    	  try {
    	  BufferedReader br= new BufferedReader(new FileReader(folderLocation+"\\stock.txt"));
    	  String line=br.readLine();
    	  int i=0;
    	  while(line!=null) { // READING THE TXT FILE UNTIL A NULL LINE
    		  System.out.println(line);
    		  line=br.readLine();
    		  list[i]=line;
    		  i++;
    		   	 
    	  }
    	  br.close();
    	  }catch (Exception e) {System.out.println("Upss.. Internal Problem, try again later!!");}
          System.out.println();
    	  logout();
      }

//////////////////////////////////////////////////////////////////////////////////
//METHOD TO SHOW THE BANK STOCK PRICES                                          //
//////////////////////////////////////////////////////////////////////////////////
      

    	public String currentDirectory() {
    			
    			String mainLocation="";
    			String patchAccount="";
    			String accountFolder="\\accounts\\"; // ACCOUNTS FOLDER
    		    
    			mainLocation=System.getProperty("user.dir"); //CURRENT WORK LOCATION
    			patchAccount=mainLocation+File.separator+accountFolder; //CURRENT WORK LOCATION PLUS ACCOUNT FOLDER
    			    			
    			return patchAccount;
    			
    		      	
    	}
      
      
      
    	   
}		


