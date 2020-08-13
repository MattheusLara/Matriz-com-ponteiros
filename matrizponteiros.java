/*
    Autor: Matheus Silva Lara
    GitHub: github.com/MattheusLara
    LinkedIn: www.linkedin.com/in/matheus-lara-0316b51b4/
*/

class Celula {
   public int elemento;
   public Celula inf, sup, esq, dir;

   public Celula(){
      this(0, null, null, null, null);
   }

   public Celula(int elemento){
      this(elemento, null, null, null, null);
   }

   public Celula(int elemento, Celula inf, Celula sup, Celula esq, Celula dir){
      this.elemento = elemento;
      this.inf = inf;
      this.sup = sup;
      this.esq = esq;
      this.dir = dir;
   }
}

class Matriz {
    private Celula inicio;
    private int linha, coluna;

    public Matriz (){
        this(3, 3);
    }

    public Matriz (int linha, int coluna){
        if(linha > 0 && coluna > 0){
            this.linha = linha;
            this.coluna = coluna;

            inicio = new Celula(-1);
            Celula aux = inicio;
            
            for(int cont = 0; cont < coluna-1;cont++){
                aux.dir = new Celula(-1);
                aux.dir.esq = aux;
                aux = aux.dir;
            }

            Celula superior = inicio;

            for(int i = 0; i < linha-1; i++){
                Celula lateral = new Celula(-1);
                lateral.sup = superior;
                superior.inf = lateral;
                aux = lateral;

                for(int j = 0; j < coluna-1;j++){
                    superior = superior.dir;

                    lateral.dir = new Celula(-1);
                    lateral.dir.esq = lateral;
                    lateral = lateral.dir;
                    lateral.sup = superior;
                    superior.inf = lateral;
                }

                superior = aux;
            }
            superior = null;
            aux = null;
        }
    }

    public void ler(){
        if(inicio != null){
            Celula colu = inicio;
            Celula lin = inicio;
            for(int i = 0; i < linha; i++){
                for(int j = 0; j < coluna; j++){
                    lin.elemento = MyIO.readInt();
                    lin = lin.dir;
                }
                colu = colu.inf;
                lin = colu;
            }
        }
    }

    public void printaMatriz(){
        if(inicio != null){
            Celula colu = inicio;
            Celula lin = inicio;
            for(int i = 0; i < linha; i++){
                for(int j = 0; j < coluna; j++){
                    MyIO.print(lin.elemento+" ");
                    lin = lin.dir;
                }
                MyIO.println("");
                colu = colu.inf;
                lin = colu;
            }
        }
    }

    
    public void mostrarDiagonalPrincipal(){  
        if(this.linha == this.coluna && this.linha > 1){
            Celula i = inicio;
            MyIO.print(i.elemento+" ");
            do{
                i = i.dir.inf;
                MyIO.print(i.elemento+" ");
            }while(i.dir != null);
            MyIO.println("");
        }
    }

    public void mostrarDiagonalSecundaria(){  
        if(this.linha == this.coluna && this.linha > 1){
            Celula i = inicio;
            for(int aux = 0 ; aux < linha-1; aux++,i = i.dir);
            MyIO.print(i.elemento+" ");
            do{
                i = i.esq.inf;
                MyIO.print(i.elemento+" ");
            }while(i.esq != null);
            MyIO.println("");
        }
    }

    public Matriz soma(Matriz A, Matriz B){
        Matriz resp = null;
        
        if(A.linha == B.linha && A.coluna == B.coluna){
            resp = new Matriz(A.linha, A.coluna);
            Celula iResp = resp.inicio;
            Celula iA    = A.inicio;
            Celula iB    = B.inicio;
  
            for(int i = 0; i < linha; i++){
                Celula jResp = iResp;
                Celula jA    = iA;
                Celula jB    = iB;

                for(int j = 0; j < coluna; j++){
                    jResp.elemento = jA.elemento + jB.elemento;  
                    jResp = jResp.dir;
                    jA = jA.dir;
                    jB = jB.dir;
                }
                iResp = iResp.inf;
                iA = iA.inf;
                iB = iB.inf;
            }
        }
        
        return resp;
    }
    

    public Matriz multiplicacao (Matriz A,Matriz B) {
        Matriz resp = new Matriz(A.linha,A.coluna);

        if(A.linha == B.linha && A.coluna == B.coluna){
            Celula lin = A.inicio;
            Celula colu = B.inicio;
            int result = 0;

            for(int i = 0; i < linha ; i++){//Varre as linhas fazendo a multiplicacao.
                Celula seguraLin = lin;//Uso para armazenar o inico da linha que estou.
                
                for(int j = 0; j < coluna; j++){//Varre a linha multiplicando as colunas;
                    Celula seguraColu = colu;//Uso para armazenar o inicio da coluna que estou.
                    
                    
                    for(int k = 0; k < coluna; k++){//Multiplica a linha atual pela coluna atual.
                        result += lin.elemento * colu.elemento;
                        lin = lin.dir;
                        colu = colu.inf;
                    }

                    resp.preencheMulti(result);//Preeche a posiçao correta da nova matriz com o resutado;
                    result = 0;//Resultado e resetado para a proxima operaçao.

                    lin = seguraLin;//Linha volta a sua posiçao inicial.
                    colu = seguraColu.dir;//Coluna volta na posiçao inicial e eh movida para a direita.
                }

                lin = seguraLin.inf;//Linha volta para a  posiçao inicio e eh movida para baixo.
                colu = B.inicio;//Coluna volta para a posiçao de inicio.
            }
        
        }

        return resp;
    }

    //Varre a matriz ate achar uma posiçao nao preenchia e a preenche com o valor de x.
    public void preencheMulti(int x){
        if(inicio != null){
            Celula colu = inicio;
            Celula lin = inicio;
            for(int i = 0; i < linha; i++){
                for(int j = 0; j < coluna; j++){
                    if(lin.elemento == -1){//Verifica se a pos esta vazia  
                        lin.elemento = x;  //Caso ache o valor a ser preenchido o seta e finaliza os laços.
                        j = coluna;
                        i = coluna;
                    }
                    lin = lin.dir;
                }
                colu = colu.inf;
                lin = colu;
            }
        }
    }

    public int getLinha(){
        return this.linha;
    }

    public int getColuna(){
        return this.coluna;
    }

}

public class matrizponteiros{
    public static void main(String[] args){
        Matriz m1; 
        Matriz m2;
        Matriz soma;
        Matriz multiplicacao;
        
        int nunOp = MyIO.readInt();
        int cont = 0;
        do{
            m1 = new Matriz(MyIO.readInt(),MyIO.readInt());
            m1.ler();
            m2 = new Matriz(MyIO.readInt(),MyIO.readInt());
            m2.ler();
            
            soma = new Matriz(m1.getLinha(),m1.getColuna());
            multiplicacao = new Matriz(m1.getLinha(),m1.getColuna());

            m1.mostrarDiagonalPrincipal();
            m1.mostrarDiagonalSecundaria();
            
            soma = soma.soma(m1,m2);
            soma.printaMatriz();

            multiplicacao = multiplicacao.multiplicacao(m1,m2);            
            multiplicacao.printaMatriz();

            cont++;
        }while(cont < nunOp);
    } 
}