/******************************************************************************
Program Discreption:	Program to display an ATM menu and get a user to enter and verify their pin, 
						change their pin, to view the number of failed and successfull pin entries, and
						an option to exit the program.
						
Name:					Thamsanqa Sibanda C18727971
Date:					11 October 2018
Language & Compiler: 	C Language, onlinegdb compiler

*******************************************************************************/
#include <stdio.h>
#define PIN_LEN 5
#define MENU_LEN 2

int main()
{
	// Declare integer variables
	int successful_attempts, failed_attempts, end_program, validator, i;
	
	// Declare character variables
	char user_menu_selection[MENU_LEN]="";
	char menu_options[MENU_LEN]="";
	char pin_entry[PIN_LEN]="";
	char new_pin1[PIN_LEN]="";
	char new_pin2[PIN_LEN]="";
	
	
	//initiate variables
	char default_pin[]="1234";
	successful_attempts=0;
	failed_attempts=0;	
	end_program=0;
	validator=0;
	i=0;
	
	// while loop to run the whole program
	while(end_program!=1)
	{
		// Display options menu
        printf("welcome to the ATM Machine.");
        printf("\n\nPlease Choose an option:");
        printf("\n1. Enter your PIN.");
        printf("\n2. Change your PIN.");
        printf("\n3. Display succesfull and failed PIN input count.");
        printf("\n4. Exit Program.\n\nEnter an Option: ");
        
        //Get user selection
        scanf("%s", &user_menu_selection);
        
        // code to check for more than 1 character from user input
        for(i=0;user_menu_selection[i] != '\0';i++);
        
        //code to assign user selection
        menu_options[0]=user_menu_selection[0];
        
        //if statement to validate user menu selection.
        if(i==1)
        {
        	// Switch Statement to select and execute a Menu Option.
	        switch(menu_options[0])
	        {
	            // case 1 Enter PIN and validate PIN
	            case '1':
	            {
	                printf("\n\nPlease enter your PIN: ");
	                scanf("%s", &pin_entry);
	                
	                // code to compare user input PIN and program PIN, and successful and failed attempts register
	                if(pin_entry[0]==default_pin[0]&&pin_entry[1]==default_pin[1]&&pin_entry[2]==default_pin[2]&&pin_entry[3]==default_pin[3])
	                {
	                    printf("\nCorrect PIN\n\n");
	                    successful_attempts++;
	                }
	                else
	                {
	                    printf("\nIncorrect PIN\n\n");
	                    failed_attempts++;
	                }// end if else statement 
	                break;
	            }// end case 1
	            
	            //case 2 option to allow PIN to be changed
	            case '2':
	            {
	            	// reset variables validator and i
	            	validator=0;
	            	i=0;
	            	// Request new PIN
	                printf("\n\nEnter new pin: ");
	                scanf("%s",&new_pin1);
	                
	                // code to find length of a user input string new PIN
				    for(i = 0; new_pin1[i] !=0; i++);
				    
				    //code to check if PIN is numbers only.
				    if(i==4)
				    {
				    	// reset variable i
				    	i=0;
				    	// code to check if input is all numbers in new pin
					    while(new_pin1[i]!='\0')
						{
							// code to validate string of new pin
							if((new_pin1[i]=='0')||(new_pin1[i]=='1')||(new_pin1[i]=='2')||(new_pin1[i]=='3')||(new_pin1[i]=='4')||(new_pin1[i]=='5')||(new_pin1[i]=='6')||(new_pin1[i]=='7')||(new_pin1[i]=='8')||(new_pin1[i]=='9'))
							{
								validator++;			
							}// end if validator
							else
							{
								printf("\n\nYour new PIN must be 4 numerical characters with no spaces\n\n");
								break;
							}// end if statement
							
							i++;
						}// end while loop number checker
																	
						// code to request user to verify PIN
						if(validator==4)
						{
							
							// Request new PIN
			                printf("\nVerify new pin: ");
			                scanf("%s",&new_pin2);
			                
			                // code to find length of a user input string of verification PIN
						    for(i = 0; new_pin2[i] !=0; i++);
						    
						    // code to re-enter PIN
						    if(i==4)
						    {
						    	// reset variables validator and i
						    	i=0;
						    	validator=0;
						    	//code to check if PIN is numbers only.
								while(new_pin2[i]!='\0')
								{
									// code to validate string of verification pin
									if((new_pin2[i]=='0')||(new_pin2[i]=='1')||(new_pin2[i]=='2')||(new_pin2[i]=='3')||(new_pin2[i]=='4')||(new_pin2[i]=='5')||(new_pin2[i]=='6')||(new_pin2[i]=='7')||(new_pin2[i]=='8')||(new_pin2[i]=='9'))
									{
										validator++;			
									}// end if statement validator for verification pin
									i++;
								}// end while loop
								
								// code to verify and update PIN
								if(validator==4)
								{
									//code to verify and update PIN
									if(new_pin1[0]==new_pin2[0]&&new_pin1[1]==new_pin2[1]&&new_pin1[2]==new_pin2[2]&&new_pin1[3]==new_pin2[3])
									{
										// reset variable and i
										i=0;
										
										//while loop to assign new PIN
										while(i<5)
										{									
											default_pin[i]=(new_pin2[i]);
											i++;
										}// End while loop
										
										printf("\n\nPIN successfully updated.\n\n");
									}
									else
									{
										printf("\n\nPIN verification failed.\n\n");
									}// end if statement
									
								}// end if statement for updating pin.
								
								
							}// end if statement, executes if length of string (i== 4)
							    
							
						}// end if statement for PIN Verification
								                					
					}//end if statement, executes if length of string (i== 4)
					else
					{
						printf("Your new PIN must be 4 numerical characters with no spaces\n\n");
					}
	                break;

	            }// end case 2
	            
	            // Option to display number of successeful and faild PIN entry attempts
	            case '3':
	            {
	                printf("\n\nNumber of successful PIN entries is %d \n", successful_attempts);
	                printf("Number of failed PIN entries is %d \n\n", failed_attempts);
	                break;
	            }// end case 3
	            
	            // Option to exit program
	            case '4':
	        	{
	        		end_program++;
	        		break;
				}// end case 4
				
				//option for invalid selection
	            default:
	            {
	            	printf("\nError, Invalid option\n\n");
	                break;
	            }// end default
	            
	        }// end switch
	        
		}// end if statement to validate user menu selection
		else
		{
			printf("\n\nError, Invalid option\n\n");
		}// end else
		
			
       
    }// End while loop Menu

    // Program End Message
    printf("\n\nYou have exited the program\n");
	return 0;
	
}// End main()
