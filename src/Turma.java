// Source code is decompiled from a .class file using FernFlower decompiler.
import java.time.Year;
import java.util.ArrayList;

public class Turma {
   private ArrayList<Aluno> alunos = new ArrayList();
   private String nome;
   private String codigo;

   public Turma(String var1) {
      this.nome = var1;
   }

   public Turma(String var1, String var2) {
      this.nome = var1;
      this.codigo = var2;
   }

   public String getCodigo() {
      return this.codigo;
   }

   public void setCodigo(String var1) {
      this.codigo = var1;
   }

   public void adicionarAluno(Aluno var1) {
      int var2 = Year.now().getValue();
      String var3 = this.nome + "/" + var2 + "-" + var1.getCpf();
      var1.matricular(var3);
      this.alunos.add(var1);
   }

   public void removerAluno(int var1) {
      if (var1 >= 0 && var1 < this.alunos.size()) {
         this.alunos.remove(var1);
      } else {
         System.out.println("\u00cdndice inv\u00e1lido.");
      }

   }

   public void removerAluno(Aluno var1) {
      if (this.alunos.remove(var1)) {
         System.out.println("Aluno removido com sucesso.");
      } else {
         System.out.println("Aluno n\u00e3o encontrado.");
      }

   }

   public void removerTodos() {
      this.alunos = new ArrayList();
   }

   public void listarAlunos() {
      for(int var1 = 0; var1 < this.alunos.size(); ++var1) {
         Aluno var2 = (Aluno)this.alunos.get(var1);
         System.out.println("Posi\u00e7\u00e3o do ArrayList [" + var1 + "] " + var2.getNome() + "/" + var2.getRga());
      }

   }
}
