import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Calculadora {
   
	

	public String InfixaParaPosFixa(String expressao) {
		Pilhas pilha = new Pilhas(expressao.length());
		String[] elementos = expressao.split(" ");
		ArrayList<String> posFixa = new ArrayList<>();
		Map<String, String> procedencias = new HashMap<String, String>() {{
            put("+", "1");
            put("-", "1");
            put("*", "2");
			put("/", "2");
			// put("(", "3");
			// put(")", "3");''
        }};

		for (String elemento : elementos) {
			String operador = null;
			if (elemento.equals("(") || elemento.equals("+") || elemento.equals("-") || elemento.equals("*")
					|| elemento.equals("/")) {

				if (!pilha.empty()) {
					String topo = pilha.top();
					if (!topo.equals("(") & !topo.equals(")") & !elemento.equals("(") & !elemento.equals("(")) {
						
					
					if (procedencias.get(((String) topo)).equals(((String)procedencias.get(elemento)))) {
						posFixa.add(pilha.pop());
						// p =("(", "(",) 
						pilha.push(elemento);
						// p =("(", "(","+") 
 					}
					else if (!procedencias.get(((String) topo)).equals(((String)procedencias.get(elemento)))) {
						pilha.push(elemento);
					}
				}
					
					else {
						pilha.push(elemento);

					}
				} else if (pilha.empty()) {
					if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
						posFixa.add(elemento);
					} else {
						pilha.push(elemento);

					}
				}

			} else if (elemento.equals(")")) {
				while (!pilha.top().equals("(")) {
					operador = pilha.pop();
					posFixa.add(operador);
				}
				pilha.pop(); 
			} else {
				posFixa.add(elemento);//
			}
		}

		StringBuilder sb = new StringBuilder();
		String separador = " ";
		String newExpressao = "";

		
		for (String elemento : posFixa) {
			sb.append(elemento).append(separador);
			newExpressao = sb.toString();
			// System.out.print(elemento + " ");
		}
		return newExpressao;
	}
	
	public String posFixaParaPreFixa(String expressao) {
		Pilhas pilha = new Pilhas(expressao.length());
		String[] elementos = expressao.split(" ");

		for (String elemento : elementos) {

			if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
				String numero2 = pilha.pop();
				String numero1 = pilha.pop();
				String newExpressao = elemento + " " + numero2 + " " + numero1;
				pilha.push(newExpressao);
			} else {
				pilha.push(elemento);
			}
		}

		return pilha.pop();
	}
	
	public String preFixaReversa(String expressao) {
		Pilhas  pilha = new Pilhas(expressao.length());
		String[] elementos = expressao.split(" ");

		for (String elemento : elementos) {
			pilha.push(elemento);
		}

		String preFixaReversa = "";
		while (!pilha.empty()) {
			preFixaReversa += pilha.pop() + " ";
		}

		return preFixaReversa.trim();
	
	}

	public String preFixaParaInfixa(String expressao) {
		String preFixaReversaStr = preFixaReversa(expressao);

		Pilhas  pilha = new Pilhas(expressao.length());
		String[] elementos = preFixaReversaStr.split(" ");

		for (String elemento : elementos) {

			if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
				String operador = elemento;
				String numero2 = pilha.pop();
				String numero1 = pilha.pop();
				String newExpressao = "( " + numero2 + " " + operador + " " + numero1 + " )";
				pilha.push(newExpressao);
			} else {
				pilha.push(elemento);
			}
		}
		
		return pilha.pop();
	}

	public Double posFixaResultado(String expressao) {
		PilhaDouble pilha = new PilhaDouble(expressao.length());

		String[] elementos = expressao.split(" ");

		for (String elemento : elementos) {

			if (elemento.equals("+") || elemento.equals("-") || elemento.equals("*") || elemento.equals("/")) {
				double numero2 = pilha.pop();
				double numero1 = pilha.pop();
				

				if (elemento.equals("+")) {
					pilha.push(numero1 + numero2);
				}

				else if (elemento.equals("-")) {
					pilha.push(numero1 - numero2);
				}

				else if (elemento.equals("*")) {
					pilha.push(numero1 * numero2);

				}

				else if (elemento.equals("/")) {
					pilha.push(numero1 / numero2);
				}
			} else {
				double num = Double.parseDouble(String.valueOf(elemento));
				pilha.push(num);
			}
		}
		return pilha.pop();

	}


	// infixa -> pos fixa *
	// infixa -> pre fixa

	// pós-fixa -> pre fixa *
	// pós-fixa -> infixa*
	
	// pré-fixa -> infixa *
	// pré-fixa -> pós-fixa
    public static void main(String[] args) {
        Calculadora calc = new Calculadora();
		Scanner scan = new Scanner(System.in);
		

		String infixa = "";
		String posfixa = "";
		String prefixa = ""; 
		double resultado = 0.0;

		System.out.println("Digite:\n");
		System.out.println("1 Para Expressão Infixa ");
		System.out.println("2 Para Expressão PosFixa ");
		System.out.println("3 Para Expressão Prefixa ");

		String opc = scan.nextLine();
		System.out.println("Agora digite a expressao de acordo:");
		String expressao = scan.nextLine();

		switch (opc) {
			case "1":
			// converter infixa para posfixa
		    posfixa =  calc.InfixaParaPosFixa(expressao);
		    // converte infixa para posfixa e depois posfixa
		    prefixa =  calc.posFixaParaPreFixa(posfixa);

			resultado = calc.posFixaResultado(posfixa);
				
				break;
			case "2":
			//converte posfixa para pre fixa
		    prefixa =  calc.posFixaParaPreFixa(expressao);
		    //converte posfixa para pre fixa depois infixa
		    infixa =  calc.preFixaParaInfixa(prefixa);

			resultado = calc.posFixaResultado(expressao);
			
			break; 	

			case "3":
			//converte de prefixa para infixa
			infixa =  calc.preFixaParaInfixa(expressao);
			//converte de prefixa para infixa e depois para pofixa
		    posfixa = calc.InfixaParaPosFixa(infixa);

			resultado = calc.posFixaResultado(posfixa);
			break; 	
				
		
			default:
				break;
		}

		System.out.println("Expressão Original: "+ expressao);
		System.out.println("Expressão Infixa: "+ infixa);
		System.out.println("Expressão Posfixa: "+ posfixa);
		System.out.println("Expressão Prefixa: "+ prefixa);
		System.out.println("Resultado: "+ resultado);

    }


}

