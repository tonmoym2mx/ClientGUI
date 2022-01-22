#include <iostream>
#include <string>
#define max 50
using namespace std;

class Process {
  public:
  int pid;
  int art;
  int bt;
  int totalBt;
  int ct;
  int wt;
  int tat;

  public:

    void printProcess() {
      printf("|   P%02d      |     %2d       |     %2d     |      %2d      |      %2d      |        %2d       |\n",
        pid, art, totalBt, ct, wt, tat);
      printf("+------------+--------------+------------+--------------+--------------+-----------------+\n");

    }

};
int n;
int timer = 0;
int tq = 2;
int completeTask = 0;
Process tempProc[max];
Process proc[max];
int c = 0;
string gcs1;
string gcs2;
string gcs3;

void printTitle() {
  printf("+------------+--------------+------------+--------------+--------------+-----------------+\n");
  printf("| Process ID | Arrival Time | Burst Time | Complete Time| Waiting Time | Turnaround Time |\n");
  printf("+------------+--------------+------------+--------------+--------------+-----------------+\n");
}

void sortbyArt() {
  int i, j;
  Process tmp;
  for (i = 0; i < n; i++) {
    for (j = i + 1; j < n; j++) {
      if (proc[j].art < proc[i].art) {
        tmp = proc[i];
        proc[i] = proc[j];
        proc[j] = tmp;
      }
    }
  }
}

void sortbyBt() {

  int i, j;
  Process tmp;
  for (i = 0; i < c; i++) {
    for (j = i + 1; j < c; j++) {
      if (tempProc[j].bt < tempProc[i].bt) {
        tmp = tempProc[i];
        tempProc[i] = tempProc[j];
        tempProc[j] = tmp;
      }
    }
  }
}

void calculateTurnaroundTime() {
  for (int i = 0; i < n; i++) {
    proc[i].tat = proc[i].ct - proc[i].art;
  }

}
void calcuateWatingTime() {
  for (int i = 0; i < n; i++) {
    proc[i].wt = proc[i].tat - proc[i].totalBt;
  }
}
void calcuateAvarageTurnaroundTime(){
    float sum = 0.0f;
    for (int i = 0; i < n; i++) {
        sum += proc[i].tat;
  }
  cout <<"Average Turnaround Time : " << sum/n<< endl;
}
void calcuateAverageWaitingTime(){
    float sum = 0.0f;
    for (int i = 0; i < n; i++) {
        sum += proc[i].wt;
    }
  cout <<"Average Waiting Time : " << sum/n<< endl;
}
void gantChartDraw() {
  string m = "    P";
  std::string s = std::to_string(tempProc[0].pid);
  m.append(s);
  m.append("    |");
  gcs2.append(m);
  gcs1.append("---------- ");
  string n = "          ";
  std::string ns = std::to_string(timer);
  n.append(ns);
  gcs3.append(n);
}
void printGantChart() {
  cout << endl;
  cout << "Gant Chart" << endl;
  cout << gcs1 << endl;
  cout << gcs2 << endl;
  cout << gcs1 << endl;
  cout << gcs3 << endl;
}

int main() {
  cout << "Enter the number of process: ";
  cin >> n;
  for (int i = 0; i < n; i++) {
    cout << "Enter arrival time and burst time for P" << i << ": ";

    cin >> proc[i].art;
    cin >> proc[i].bt;
    proc[i].pid = i;
    proc[i].totalBt = proc[i].bt;

  }

  sortbyArt();

  while (1) {
    c = 0;
    for (int i = 0; i < n; i++) {
      if (proc[i].art <= timer && proc[i].bt > 0) {
        tempProc[c] = proc[i];
        c++;
      }
    }

    sortbyBt();

    if (c > 0) {
      int bt = proc[tempProc[0].pid].bt;
      if (bt >= tq) {
        proc[tempProc[0].pid].bt = (bt - tq);
        timer += tq;
      } else {
        proc[tempProc[0].pid].bt = 0;
        timer += bt;
      }

      gantChartDraw();
      if (proc[tempProc[0].pid].bt == 0) {

        proc[tempProc[0].pid].ct = timer;
        completeTask++;
      }
    }

    if (completeTask == n)
      break;

  }
  calculateTurnaroundTime();
  calcuateWatingTime();


  printf("\n");
  printTitle();
  for (int i = 0; i < n; i++)
    proc[i].printProcess();


  calcuateAvarageTurnaroundTime();
  calcuateAverageWaitingTime();

  printGantChart();



  return 0;
}
