/*********************************************************************************************************************
Program Desciption: Assignment 2
					Program that will perform security authorisation based on access codes.
					The program will display a 5 option menu which will allow a user to: 
					
					1. Enter a code, only if there is not an encrypted coded already entered.
					2. Encrypt and verify if correct, only when there is a code entered and is not already encrypted.
					3. Decrypt the code which would have been encrypted from the previous option only.
					4. Display the number of successful and failed code entries.
					5. End the program.

					

Date: 24 February 2019
Name: Thamsanqa Sibanda
Language & Compiler: C Programming Language, Dev-C++ compiler
**********************************************************************************************************************/

#include <stdio.h>
#include <string.h>  
#define code_size 4
#define Menu_Len 200

// Prototypes
int* enter_code(int *);
void encrypt_code(int *,int *, int *,int *, int *);
void decrypt_code(int *,int *);
void display_attempts(int *, int *);
void exit_msg(void);


int main()
{
	// Declare variables
	int successful_attempts=0;
	int failed_attempts=0;
	int i;
	int access_code[code_size]={4,5,2,3};
	int *usr_acs_cde;
	int encryption_check=0;
	int str_len;
	char menu_options[Menu_Len]="";
	
    printf("\nWelcome.");

	//Display Menu
	do
	{
		// Display options menu
        printf("\n\nPlease Choose an option.");
        printf("\n1. Enter code.");
        printf("\n2. Encrypt code and verify if correct.");
        printf("\n3. Decrypt code.");
        printf("\n4. Display Successfull and failed access code attempts. ");
        printf("\n5. Exit Program.\n");
        printf("\n\nEnter an option: ");


		//Get selection from user
		gets(menu_options);
		
		if(strlen(menu_options)>1)
		{
			printf("Invalid input, to select a menu option please enter 1 numeric value between 1 and 5.");
			*(menu_options+0)='\0';
			//printf("\n%s\n", menu_options);
		}// end if
		else
		{			
			//Display and execute menu
			switch(*(menu_options))
			{
				case '1':
				{
					// enter user access code
					usr_acs_cde=enter_code(&encryption_check);		
					break;
				}// end case 1
				
				case '2':
				{
					encrypt_code(usr_acs_cde,access_code, &encryption_check, &failed_attempts, &successful_attempts);
					break;
				}// end case 2
				
				case '3':
				{
					decrypt_code(usr_acs_cde, &encryption_check);
					break;
				}// end case 3
				
				case '4':
				{
					display_attempts(&successful_attempts,&failed_attempts);
					break;
				}// end case 4
				
				case '5':
				{
					exit_msg();
					break;
				}// end case 5
				
				default:
	            {
	            	printf("Invalid input, to select a menu option please enter 1 numeric value between 1 and 5.");
	                break;
	            }// end default
			}// end switch
		}// end else
		
	}// End do while
	while(*(menu_options)!='5');

	return 0;
}// end main()



// Implement function enter_code()
int* enter_code(int *crypt_chk)
{
	// Declare variables
	char user_code[code_size+1]="";
	char tmp_string[2]="";
	static int usr_cde[code_size];
	int tmp_num;
	int i;
	int chkr;


	if(*(crypt_chk)==0||*(crypt_chk)==1)
	{
	
		while(chkr!=4)
		{
			// get the user code
			printf("\nEnter Access Code: ");
			gets(user_code);
			
			if(strlen(user_code)>4)
			{
				printf("\nInvalid input. The access code must be 4 numerical characters.\nPlease try again.\n");
			}// end if
			else if(strlen(user_code)<4)
			{
				printf("\nInvalid input. The access code must be 4 numerical characters.\nPlease try again.\n");
			}// end if
			else
			{				
				// Check if user input contains numbers only and is 4 digits long
				chkr=0;
				for(i=0;i<code_size+1;i++)
				{
					if(*(user_code+i)>='0'&&*(user_code+i)<='9')
					{
						chkr++;
					}// end if 
					
				}// end for loop
				
				// Convert string to numbers and validate if user input is valid
				if(chkr==4)
				{
					// Convert string of numbers to an array of intergers
					printf("\nThank you for your input.");
					for(i=0;i<code_size;i++)
					{
						*(tmp_string+0)=*(user_code+i);
						tmp_num=atoi(tmp_string);
						*(usr_cde+i)=tmp_num;
					}// end for loop
					*crypt_chk=1;
					
				}// end if
				else
				{
					printf("\nInvalid input. The access code can only be 4 numerical characters\nPlease Try again.\n");
				}// end else
			}// end else
			
		}// end while
		
	}// end if
	else if(*(crypt_chk)==2)
	{
		printf("\nA code has already been entered and encrypted, please decrypt current code, option 3 in the main menu");
	}// end else if
	
	return usr_cde;
}// end function enter_code



// implement function  encrypt_code()
void encrypt_code(int *u_acs_cd,int *d_acs_cde, int *crypt_chk, int *fails, int *succss)
{
	int i, j, tmp_num, chk;

	if(*(crypt_chk)==1)
	{
		// encrypt user code
		for(i=0;i<code_size-2;i++)
		{
			// swap 1st with 3rd and 2nd with 4th
			tmp_num=*(u_acs_cd+i);
			*(u_acs_cd+i)=*(u_acs_cd+i+2);
			*(u_acs_cd+i+2)=tmp_num;
			if(i==1)
			{
				for(j=0;j<code_size;j++)
				{
					*(u_acs_cd+j)=*(u_acs_cd+j)+1;
					if(*(u_acs_cd+j)==10)
					{
						*(u_acs_cd+j)=0;
					}// end inner if
				}// end inner for loop
			}// end if
		}// end encryption for loop
		
		
		// check if user code matches access_code and record failed and successfull code authenitcation
		chk=0;
		for(i=0;i<code_size;i++)
		{
			if(*(u_acs_cd+i)==*(d_acs_cde+i))
			{
				chk++;
			}// end if
		}// end for loop
		
		// Display success or failed authentication
		if(chk==4)
		{
			printf("\nCorrect Code entered");
			*succss=(*succss)+1;
		}// end if
		else
		{
			printf("\nWrong Code entered");
			*fails=(*fails)+1;
		}// end else
		*crypt_chk=2;
		
	}// end if
	else if(*(crypt_chk)==0)
	{
		printf("\nNo valid code entered. Please select option 1 and enter a code.");
	}// end else if
	else
	{
		printf("\nA code has already been entered and encrypted, please decrypt current code, option 3 in the main menu");
	}// end else
	
}// end function encrypt_code()



// Implment function decrypt_code()
void decrypt_code(int *usr_code, int *encrypted)
{
	// Declare variables
	int i, j, tmp_num;

	// decrypt user code
	if(*(encrypted)==2)
	{
		// Decrypt usr_code
		for(i=0;i<code_size-2;i++)
		{
			// step 1 for decryption
			if(i==0)
			{
				for(j=0;j<code_size;j++)
				{
					*(usr_code+j)=*(usr_code+j)-1;
				}// end inner for loop
				
			}// end if
			
			// step 2 for decryption
			tmp_num=*(usr_code+i);
			*(usr_code+i)=*(usr_code+i+2);
			*(usr_code+i+2)=tmp_num;
	
		}// end decryption for loop
		printf("Code decrypted successfully");
		*(encrypted)=1;
		
	}// end if 
	else if(*(encrypted)==1)
	{
		printf("\nDecryption failed, select option 2 to encrypt current code or option 1 to enter a new code");
	}// end if
	else
	{
		printf("\nDecryption failed, no valid code entered. Please select option 1 and enter a code.");
	}// end else
	
}// end decrypt_code()



// Implement function display_attempts()
void display_attempts(int *sucss, int *fail)
{
	//Display failed and successful code authentication
	printf("\nAcccess code entered successfully: %d",*sucss);
	printf("\nAcccess code entered Incorrectly: %d",*fail);
}// end display_attempts



// Implement function exit_msg();
void exit_msg(void)
{
	//End programe gracefully
	printf("\n\n************************ Program ended ************************");
	printf("\n\n");


}// end exit_msg()
