import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

public class TestSpoon {
	public static void main(String[] args) {
			SpoonAPI spoon = new Launcher();
			spoon.addInputResource("./res");
			spoon.buildModel();
			
			// Methods
			for (CtType<?> type: spoon.getModel().getAllTypes() ) {
				System.out.println("Class: " + type.getQualifiedName());
				
				// Methods
				for(CtMethod<?> meth: type.getMethods()) {
					System.out.println(meth.getSimpleName());
					System.out.println("- Method: " + meth.getSignature());
					int inicio = meth.getBody().getPosition().getLine();
					int fim = meth.getBody().getPosition().getEndLine();
					System.out.println("Quantidade de linhas: " + (fim - inicio));
				}
				
				//Fields
				for(CtField<?> field: type.getFields()) {					
					System.out.println("- Field: " + field.getSimpleName());					
					System.out.println("- Modfiers: " + field.getModifiers());
					System.out.println("- Location: " + field.getPosition().getLine());
				}
				
				
			}
			
			
			
	}
}
