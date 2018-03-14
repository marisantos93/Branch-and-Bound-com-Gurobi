package testespo;


import gurobi.GRBException;
import java.util.LinkedList;
import java.util.Queue;

public class BB {
    
    public static void main (String[] args) throws GRBException{
		
        
        BB bb = new BB();
        Queue <Modelo> fila = new LinkedList<Modelo>();// fila que armazena os modelos
        Modelo mOtimo = null;
        int m = 2; //restricao
        int n = 2; //var
        double A[][] = {{1,1},{10,6}}; //coeficiente das restrições
        int b[] = {5,45};
        int c[] = {5,4};
        int sinal[] = {1,1}; //1 é menor igual, 2 é maior igual, 0 é igual
        
            Modelo m1 = new Modelo(m,n,A,b,c,sinal);
            fila.add(m1); //adiciona o modelo m1 na fila
            
        while(!fila.isEmpty()){  
                  
            Modelo mod = fila.poll(); //removendo o primeiro modelo da fila
            
            primeiroNo p = new primeiroNo();
            p.primeiroNo(mod);

            boolean solInteira = true;
            
            //para cada variavel ele cria dois modelos
            //um arredonda p baixo
            //outro arredonda p cima
            if(mod.getZ() == -1)
                continue;
            
            for(int k=0; k<mod.getN();k++){
            
                if(mod.getResultado()[k] %1 == 0){ //verifica se var k é inteira
                    continue;                       //se for inteira não executa passos a seguir
                }
                solInteira = false;
                
            
                //GERO OS VALORES PARA O MENOR INTEIRO MAIS PROXIMO
                double Anovo[][] = new double[mod.getM()+1][mod.getN()];
                //copia matriz A anterior
                for(int i=0; i<mod.getM();i++)
                    for(int j=0; j<mod.getN();j++)
                        Anovo[i][j] = mod.getA()[i][j]; 
                
                //inserir nova linha na matriz A
                for(int j=0; j<mod.getN();j++){
                    if (j==k)
                        Anovo[mod.getM()][j] = 1;
                    else
                        Anovo[mod.getM()][j] = 0;
                }
                
                //criar um mnovo
                int mnovo = mod.getM()+1;
                //criar vetor bnovo
                int bnovo[] = new int[mod.getM()+ 1];
                //copio o vetor b para bnovo
                for(int r=0; r<mod.getM(); r++){
                    bnovo[r] = mod.getB()[r];
                }
                         
                //add uma nova linha a bnovo
                //add o valor acima
                bnovo[mod.getM()]= (int)mod.getResultado()[k];
                    
               
                //crio vetor sinalnovo
                int sinalnovo[]= new int [mod.getM()+1];
                for(int q=0; q<mod.getM(); q++){
                    sinalnovo[q] = mod.getSinal()[q];
                }
                
                sinalnovo[mod.getM()]= 1;
                
                Modelo modNovo = new Modelo(mnovo, mod.getN(), Anovo, bnovo, mod.getC(), sinalnovo);
                fila.add(modNovo);
                
                
                //criar 2º modelo
                double Anovo2[][] = new double[mod.getM()+1][mod.getN()];
                //copia matriz A anterior
                for(int i=0; i<mod.getM();i++)
                    for(int j=0; j<mod.getN();j++)
                        Anovo2[i][j] = Anovo[i][j]; 
                
                //inserir nova linha na matriz A
                for(int j=0; j<mod.getN();j++){
                    if (j==k)
                        Anovo2[mod.getM()][j] = 1;
                    else
                        Anovo2[mod.getM()][j] = 0;
                }
                
                int bnovo2[] = new int[mod.getM()+1];
                for(int r=0; r<mod.getM(); r++){
                    bnovo2[r] = mod.getB()[r];
                }
                bnovo2[mod.getM()]=(int)mod.getResultado()[k]+1;
                
                int sinalnovo2[]= new int [mod.getM() +1];
                for(int r=0; r<mod.getM(); r++){
                    sinalnovo2[r] = mod.getSinal()[r];
                }

                sinalnovo2[mod.getM()] = 2;

                Modelo modNovo2 = new Modelo(mnovo, mod.getN(), Anovo2, bnovo2, mod.getC(), sinalnovo2);
                fila.add(modNovo2);
            }
            if(solInteira==true)
                if (mOtimo == null)
                    mOtimo = mod;            
                else if(mOtimo.getZ() < mod.getZ())
                        mOtimo = mod;

        }
        System.out.println("******* Solucao Otima *******");
        System.out.println("x1 é: " + mOtimo.getResultado()[0]);
        System.out.println("x2 é:" + mOtimo.getResultado()[1]);
        System.out.println("Z é:" + mOtimo.getZ());
     
    }
}
