package railway_ticket_booking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TicketBooker {
    static int pnrNo=1;
	static int ticketNo=1;
	static int noOfSeats=8;
	static int noOfStops=5;
	static int totalWaiting=2;
	static int availWaiting=totalWaiting;
	static HashMap<Integer,Pnr> pnrList=new HashMap<>();
	static Queue<Integer> waitingList=new LinkedList<>();
	static char[][] chart=new char[noOfSeats][noOfStops];

		public static void main(String[] args) {
		System.out.println("***********************************************************************");
		System.out.println("**                   Welcome to Krishmoor railways                   **"); 
		System.out.println("***********************************************************************");
		System.out.println();
		Scanner ob=new Scanner(System.in);
		int choice;
		do
		{
		System.out.print("Operations......\n1.Book ticket\n2.Cancel ticket\n3.View user\n4.Print Ticket Details\n5.Exit\n\nEnter the choice : ");
		choice=ob.nextInt();
		switch(choice)
		{
		case 1:
			bookTickets();
			break;
		case 2:
			cancelTickets();
			break;
		case 3:
			viewChart();
			break;
		case 4:
			System.out.println("Enter PNR id : ");
			printTicketDetails(ob.nextInt());
			break;
		case 5:
			System.exit(0);
		default :
			System.out.println("Enter valid option!!!");
		}
		}while(choice!=0);

	}
	
	static void bookTickets()
	{
		Scanner ob=new Scanner(System.in);
		char from=ob.next().charAt(0);
		int start=(int)(from)-65;
		char to=ob.next().charAt(0);
		int end=(int)(to)-65;
		int noOfTickets=ob.nextInt();	
		int i=0,j;
		int booked=0;
        ArrayList<Integer> bookedSeats=new ArrayList<>();
		ArrayList<Ticket> tickets=new ArrayList<>();
		bookedSeats=getUnfilledTickets(start, end,noOfTickets);
        booked=bookedSeats.size();
        if(noOfTickets==booked||noOfTickets<=booked+availWaiting)
        {
            prepareChart(start, end, booked, bookedSeats);
            Pnr pnr=new Pnr();
            pnrList.put(pnrNo, pnr);
            pnr.setPnrId(pnrNo);
            pnr.setFrom(from);
            pnr.setTo(to);
            for(i=0;i<booked;i++)
            {
                int seat=bookedSeats.get(i);
                tickets.add(new Ticket(ticketNo++,"booked",seat ));
            }
            if(booked<noOfTickets)
            {
                for(i=0;i<noOfTickets-booked;i++)
                {
                    waitingList.add(pnrNo);
                    tickets.add(new Ticket(ticketNo++,"waiting",0));
                    availWaiting--;
                }
            }
            pnr.setTickets(tickets);
            pnr.setTotalTickets(noOfTickets);
            System.out.println("\nPNR-"+(pnrNo++)+"booked with "+booked+" confirmed and "+(noOfTickets-booked)+" Waiting tickets");

        }
        else{
            System.out.println("\nSorry, Out Of Tickets!!!");
        }
			
	}
	static void prepareChart(int start,int end,int booked,ArrayList<Integer> bookedSeats)
    {
        for(int i=0;i<booked;i++)
        {
            int seat=bookedSeats.get(i);
            for(int j=start;j<end;j++)
            {
                chart[seat-1][j]='*';
            }
        }
    }
	static void cancelTickets()
	{
		System.out.print("Enter PNR ID : ");
        Scanner ob=new Scanner(System.in);
        int pnrId=ob.nextInt();
        System.out.print("\nNo of tickets to cancel : ");
        int noOfTickets=ob.nextInt();
        ArrayList<Ticket> tickets;
        if(pnrList.containsKey(pnrId))
        {
            Pnr pnr=pnrList.get(pnrId);
            if(noOfTickets>pnr.getTotalTickets())
            {
                System.out.println("Check the no of tickets you have!!!");
            }
            else
            {
                int start=pnr.getFrom()-65;
                int end=pnr.getTo()-65;
                int seat,cancelled=0;
                pnr.setTotalTickets(pnr.getTotalTickets()-noOfTickets);
                for(int i=0;i<noOfTickets;i++)
                {
                    seat=((Ticket)pnr.getTickets().get(0)).getSeatNo();
                    for(int j=start;j<end;j++)
                    {
                        chart[seat-1][j]=' ';
                    }
                    pnr.getTickets().remove(0);
                    cancelled++;
                }
                System.out.println(cancelled+" Tickets cancelled from PNR-"+pnrId);
                if(pnr.getTotalTickets()==0)
                {
                    pnrList.remove(pnrId);
                }
                if(!waitingList.isEmpty())
                {
                    Pnr waitingPnr=pnrList.get((int)waitingList.peek());
                    int waiting_start=(waitingPnr.getFrom())-65;
                    int waiting_end=(waitingPnr.getTo())-65;
                    ArrayList<Integer> seats=getUnfilledTickets(waiting_start, waiting_end,1);
                    while (seats.size()!=0) 
                    {
                        int totalTickets=waitingPnr.getTotalTickets();
                        tickets=waitingPnr.getTickets();
                        for(int i=0;i<totalTickets;i++)
                        {
                            if(tickets.get(i).getStatus()=="waiting")
                            {
                                tickets.get(i).setStatus("booked");
                                tickets.get(i).setSeatNo(seats.get(0));
                                System.out.print("PNR-"+waitingList.peek()+" ");
                                waitingList.remove();
                                availWaiting++;
                                prepareChart(waiting_start, waiting_end, 1, seats);
                                break;    
                            }
                        }
                        System.out.print("Confirmed from waiting list");
                        if(!waitingList.isEmpty())
                        {
                            waitingPnr=pnrList.get((int)waitingList.peek());
                            waiting_start=(waitingPnr.getFrom())-65;
                            waiting_end=(waitingPnr.getTo())-65;
                            seats=getUnfilledTickets(waiting_start, waiting_end,1);
                        }
                        else
                        {
                            break;
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("Enter valid PNR!!!");
        }

	}
	
	static void viewChart()
	{
		System.out.println("   A  B  C  D  E");
		for(int i=0;i<noOfSeats;i++)
		{
			System.out.print(" "+(i+1)+" ");
            for(int j=0;j<noOfStops;j++)
            {
                if(chart[i][j]!='*')
                {
                    System.out.print("   ");
                    continue;
                }
                System.out.print(chart[i][j]+"  ");
            }
            System.out.println();
		}
	}	
	static void printTicketDetails(int pnrId)
	{
		if(pnrList.containsKey(pnrId))
        {
            Pnr pnr=pnrList.get(pnrId);
            int totalTickets=pnr.getTickets().size();
            System.out.println("PNR "+pnrId);
            System.out.println("From : "+pnr.getFrom());
            System.out.println("To : "+pnr.getTo());
            System.out.println("Total Tickets : "+pnr.getTotalTickets());
            for(int i=0;i<totalTickets;i++)
            {
                Ticket ticket=(Ticket) pnr.getTickets().get(i);
                System.out.println("Ticket "+(i+1)+" Details\n");
                System.out.println("Ticket No : "+ticket.getTicketNo());
                System.out.println("Status : "+ticket.getStatus());
                System.out.println("Seat no : "+ticket.getSeatNo());
            }

        }
	}

    static ArrayList<Integer> getUnfilledTickets(int start,int end,int noOfTickets)
    {
        ArrayList<Integer> bookedSeats=new ArrayList<>();
        int j;
        for(int i=0;i<noOfSeats && noOfTickets>0;i++)
        {
            for(j=start;j<end;j++)
		    {
			    if(chart[i][j]=='*')
				    break;
		    }
		    if(j==end)
		    {
                bookedSeats.add(i+1);
                noOfTickets--;
		    } 

        }
        return bookedSeats;    
    }
}
