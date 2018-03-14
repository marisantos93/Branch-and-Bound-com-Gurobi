package testespo;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class primeiroNo {
          
    GRBVar [] variavel = new GRBVar[2]; //variaveis
    
    public void primeiroNo(Modelo mod) throws GRBException{
        
        BB bb = new BB();
        
       // Pego a memoria total da JVM e subtraio pela memoria livre na JVM para obter a utilizada
       long memuse = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());

        //tempo de execuçao inicial
        long tempoini = System.currentTimeMillis();

        
    try {
      GRBEnv    env   = new GRBEnv("bb.log");
      GRBModel  model = new GRBModel(env);

      // Crio as variaveis
      variavel[0] = model.addVar(0, GRB.INFINITY, 1, GRB.CONTINUOUS, "x1");
      variavel[1] = model.addVar(0, GRB.INFINITY, 1, GRB.CONTINUOUS, "x2");


      // Cria a F.O.
      GRBLinExpr expr = new GRBLinExpr();
      
      for (int f=0; f<mod.getN(); f++){
          expr.addTerm(mod.getC()[f], variavel[f]);
          model.setObjective(expr, GRB.MAXIMIZE);
      }

      
      // Cria as restrições
      expr = new GRBLinExpr();
      
      for (int i =0; i <mod.getM(); ++i){
          expr = new GRBLinExpr();
          for(int j=0; j<mod.getN();j++){
              expr.addTerm(mod.getA()[i][j], variavel[j] );
              }
            if(mod.getSinal()[i] == 1){
                model.addConstr(expr, GRB.LESS_EQUAL, mod.getB()[i], "rt" + i);
            }else if(mod.getSinal()[i] == 2){
                model.addConstr(expr, GRB.GREATER_EQUAL, mod.getB()[i], "rt" + i);
            }else{
                model.addConstr(expr, GRB.EQUAL, mod.getB()[i], "rt" + i);
            }
          }  

      // Otimiza o modelo
      
      model.optimize();

      //tempo de execuçao final
      long tempofim = System.currentTimeMillis()- tempoini;
      primeiroNo p = new primeiroNo();
      
      //Armazeno os resultados das variaveis 1 e 2 no vetor de resultados
      for(int r=0; r<mod.getN(); r++){
          mod.getResultado()[r]= variavel[r].get(GRB.DoubleAttr.X);
          mod.getRaux()[r] = (int)mod.getResultado()[r];
      }
      double g = model.get(GRB.DoubleAttr.ObjVal);
      mod.setZ(g);
      
     //se resposta for inviavel ele para.
      if (model.get(GRB.IntAttr.Status) == GRB.INFEASIBLE || model.get(GRB.IntAttr.Status) == GRB.UNBOUNDED 
              || model.get(GRB.IntAttr.Status) == GRB.INF_OR_UNBD){
          mod.setZ(-1);       
      }
     
      System.out.println("******************SOLUÇÃO**********************");
      System.out.println("Valor de Z=: " + mod.getZ());
      for(int r=0; r<2; r++){
          System.out.println(variavel[r].get(GRB.StringAttr.VarName)
                         + " " + variavel[r].get(GRB.DoubleAttr.X));
      }
      System.out.println("O tempo de execução foi de:" +tempofim + " milisegundos");
      System.out.println("A memória utilizada foi de: " + memuse /2048 + " Mb");
      System.out.println("***********************************************");


      // Dispose of model and environment
      model.dispose();
      env.dispose();
      

    } catch (GRBException e) {
      System.out.println("Error code: " + e.getErrorCode() + ". " +
                         e.getMessage());
    }
    
  }
    
}
