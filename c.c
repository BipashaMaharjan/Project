#include<stdio.h>
#include<string.h>
#include<stdlib.h>

struct Monthly_Budget{
	char name[50];
	int budget;
	int expenses;
	int underover;
}m;

void add();
void del();
void search();
void view();


int main(){
	system("CLS");
	int choice;
	printf("*********************************");
	printf("\n");
    printf(" <==:Monthly Budget Expenses:==> ");
	printf("\n");
    printf("*********************************");
	printf("\n");
    printf("1. Add Record");
	printf("\n");
    printf("2. Delete Record");
	printf("\n");
	printf("3. Search record");
	printf("\n");
    printf("4. View all the details ");
	printf("\n");
	printf("*********************************");
	printf("\n");
	printf("Enter your choice:");
    scanf("%d", &choice);
   
    switch(choice){
    	case 1:
    		add();
    		break;
    
		case 2:
    		del();
    		break;
    	
		case 3:
    		search();
    		break;
			    			
    	case 4:
    		view();
    		break;
    		
    	case 5:
    		exit();
    		break;
    		
		default:
        	printf("\nInvalid!");
        	break;
	}
}
void add(){
	FILE *ptr;
	printf("\n");
	printf("*********************************");
	printf("\n");
	printf("<==:ADD Details:==>\n");
	printf("---------------------------------");
	printf("\n");
    printf("Please Enter the details:\n");
	printf("\n");
    printf("Category: ");
    scanf("%s",m.name);
 	printf("\n");
    printf("Budget: ");
    scanf("%d",&m.budget);
	printf("\n");
    printf("Spent: ");
    scanf("%d",&m.expenses);
    printf("\n");
    printf("Over/Under the budget?\n");
    m.underover=m.budget-m.expenses;
    printf("%d\n",m.underover);
    
    ptr=fopen("Monthly expenses.txt","a");
	fwrite(&m,sizeof(m),1,ptr);
	fclose(ptr);
	printf("\n");
    printf("Press any key to continue!");
    getch();
    main();
}

void del(){
		FILE *ptr,*p;
	char n[50];
	int flag=0;
	printf("\n<==:DELETE category:==>\n");
	printf("\nEnter Name: ");
	scanf(" %s",n);
    ptr=fopen("Monthly Expenses.txt","r");
	if(ptr==NULL)
	{
		printf("\n\tFile Not Found!");
	}
	else
	{
		p=fopen("p.txt","w");
		while(fread(&m,sizeof(m),1,ptr))
		{
        	if(strcmp(m.name,n)!=0)
			{
				fwrite(&m,sizeof(m),1,p);
			}
        	if(strcmp(m.name,n)==0)
				flag=1;
		}
		fclose(ptr);
		fclose(p);
        if(flag!=1)
		{
			printf("\nRecord not Found!");
			remove("p.txt");
		}
		else if(flag)
		{
			remove("Monthly Expenses.txt");
			rename("p.txt","Monthly Expenses.txt");
			printf("\nDetails Deleted!");
		}
	}
    printf("\nPress any key to continue!");
    getch();
    main();
	
}

void search(){
	system("cls");
    char n[50];
    int flag=0;
    FILE *ptr;
    printf("\n<==:SEARCH CATEGORY:==>\n");
    printf("\nEnter Name: ");
    scanf(" %s",n);
    ptr=fopen("Monthly expenses.txt","r");
    if(ptr!=NULL){
    	printf("\n\t%-20s%-20s%-20s%-20s\n","Category","Budget","Spent","Over/Under");
   		printf("    ----------------------------------------------------------------------------");  
    	while(fread(&m,sizeof(m),1,ptr)){
            	if (strcmp(m.name,n)==0)
            	{
            		printf("\n\t%-20s%-20d%-20d%-20d\n",m.name,m.budget,m.expenses,m.underover);
                	flag=1;
            	}
        	}
        	if(flag!=1)
        	printf("\tCategory Not Found!\n");
	}
	else
	printf("\n\tError!!File Not Found!\n");
    fclose(ptr);
    printf("\n\tPress any key to continue!");
    getch();
    system("cls");
    main();
}

void view(){
	system("cls");
    FILE *ptr;
    printf("\n<==:VIEW DETAILS:==>\n");
        ptr=fopen("Monthly expenses.txt","r");
    if (ptr==NULL)
    {
        printf("\n\tError!!File Not Found!\n");
    }
    else
    {
   	    printf("\n\t%-20s%-20s%-20s%-20s\n","Category","Budget","Spent","Over/Under");
   		printf("    ----------------------------------------------------------------------------");    
        while(fread(&m,sizeof(m),1,ptr))        
        {
        	
            printf("\n\t%-20s%-20d%-20d%-20d\n",m.name,m.budget,m.expenses,m.underover);
        }
    }
    fclose(ptr);
    printf("\nPress any key to continue!");
    getch();
    system("cls");
    main();	
}
