package condominiosDemo.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ResidentsGenerator {

    private static final Random random = new Random();

    /**
     * Genera un nombre aleatorio con nombre y apellido
     */
    public static String fullNameGenerator() {
        List<String> firstNames = Arrays.asList(
                "Juan", "Maria", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Sofia",
                "Miguel", "Isabel", "David", "Elena", "Javier", "Carmen", "Daniel", "Paula",
                "Alejandro", "Lucia", "Roberto", "Marta", "Ricardo", "Sara", "Jorge", "Patricia",
                "Manuel", "Raquel", "Sergio", "Andrea", "Angel", "Beatriz", "Adrian", "Hector", "Ivan",
                "Oscar", "Raul", "Simon", "Victor", "Alan", "Bruno", "Cesar", "Fabian", "Gaston", "Ignacio",
                "Kevin", "Nestor", "Ramon", "Ulises", "Walter", "Yago", "Zacarias", "Abel", "Axel", "Boris", "Ciro",
                "Dylan", "Ethan", "Felix", "Gael", "Hugo", "Iker", "Joel", "Kilian", "Liam", "Noah",
                "Omar", "Quentin", "Romeo", "Thiago", "Uriel", "Xavier", "Santiago", "Gabriel", "Samuel", "Mateo",
                "Elias", "Lucas", "Emilio", "Julian", "Martin", "Alonso", "Valeria", "Camila", "Gabriela", "Natalia",
                "Victoria", "Renata", "Regina", "Jimena", "Emilia", "Antonella",
                "Esteban", "Alvaro", "Marcos", "Francisco", "Gonzalo", "Benjamin", "Felipe", "Mario", "Ruben", "Erick",
                "Adriana", "Carolina", "Diana", "Jessica", "Silvia", "Montserrat", "Brenda", "Esther", "Aitana", "Nicole"
        );

        List<String> lastNames = Arrays.asList(
                "Garcia", "Rodriguez", "Gonzalez", "Lopez", "Martinez", "Perez", "Sanchez",
                "Fernandez", "Ruiz", "Diaz", "Moreno", "Alvarez", "Romero", "Gutierrez",
                "Torres", "Jimenez", "Vargas", "Silva", "Castillo", "Ortega", "Herrera",
                "Castro", "Ramos", "Medina", "Navarro", "Iglesias", "Leon", "Cano", "Cruz",
                "Mendez", "Aguilar", "Blanco", "Cabrera", "Delgado", "Escobar", "Flores",
                "Gallardo", "Hernandez", "Infante", "Juarez", "Krauss", "Lara", "Maldonado",
                "Nieto", "Orellana", "Palacios", "Quiroga", "Reyes", "Salas", "Toscano", "Uribe",
                "Valdez", "Zamora", "Acosta", "Benitez", "Cortes", "Dominguez", "Estrada", "Fuentes",
                "Gomez", "Herranz", "Ibarra", "Jara", "Kramer", "Leiva", "Manzano", "Nunez", "Osorio",
                "Pardo", "Quintana", "Saez", "Tellez", "Vazquez", "Zarate",
                "Soto", "Molina", "Rojas", "Marquez", "Mora", "Munoz", "Serrano", "Campos",
                "Miranda", "Pena", "Bravo", "Rivera", "Morales", "Gil", "Rubio", "Mesa",
                "Valencia", "Moya", "Parra", "Montes", "Arias", "Vidal", "Mendoza", "Rios",
                "Vega", "Calvo", "Sarmiento", "Pizarro", "Rivas", "Velazquez", "Solis", "Lozano",
                "Montenegro", "Velasco", "Chacon", "Cuevas", "Fajardo", "Hidalgo", "Milla", "Vera"
        );

        List<String> keywords = Arrays.asList("Resident", "Cliente", "Client", "User", "Usuario", "Member", "Miembro");

        String randomFirstName = firstNames.get(random.nextInt(firstNames.size()));
        String randomLastName = lastNames.get(random.nextInt(lastNames.size()));

        // Decide aleatoriamente la sustitución
        List<String> substitutionOptions = Arrays.asList("first_name", "last_name", "none");
        String substitution = substitutionOptions.get(random.nextInt(substitutionOptions.size()));

        if (substitution.equals("first_name")) {
            randomFirstName = keywords.get(random.nextInt(keywords.size()));
        } else if (substitution.equals("last_name")) {
            randomLastName = keywords.get(random.nextInt(keywords.size()));
        }

        return randomFirstName + " " + randomLastName;
    }

    /**
     * Generador de correos aleatorios basado en un nombre completo
     */
    public static String emailGenerator(String fullName) {
        List<String> keywords = Arrays.asList("QA", "Demo", "Happier", "User", "Test", "CF");

        // Separar el nombre y apellido (Split por espacio)
        String[] parts = fullName.split(" ");
        String firstName = parts[0];
        String lastName = (parts.length > 1) ? parts[1] : "";

        // Generar números aleatorios de dos dígitos con ceros a la izquierda (zfill equivalente)
        String num1 = String.format("%02d", random.nextInt(99) + 1);
        String num2 = String.format("%02d", random.nextInt(99) + 1);

        // Seleccionar palabra clave aleatoria
        String keyword = keywords.get(random.nextInt(keywords.size()));

        // Construir el correo
        return firstName + num1 + lastName + keyword + num2 + "@yopmail.com";
    }

    // Método para ejecución interna
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE GENERADOR DE USUARIOS ===");
        for (int i = 0; i < 5; i++) {
            String nombre = fullNameGenerator();
            String correo = emailGenerator(nombre);
            System.out.println("Prueba " + (i + 1) + ":");
            System.out.println("   Nombre: " + nombre);
            System.out.println("   Correo: " + correo);
            System.out.println("---------------------------------------");
        }
    }
}