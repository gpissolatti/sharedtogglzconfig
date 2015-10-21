#Togglz, com o MongoDB e Spring Security
 
Para utilização do Togglz, deve-se importar a seguinte dependência no pom.xml do projeto:

```
<dependency>
	<groupId>com.utils</groupId>
	<artifactId>sharedtogglzconfig</artifactId>
	<version>1.0</version>
</dependency>
```
Cria um enum para as Features no Togglz:

```
import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;
 
public enum ProjectNameTogglzFeatures implements Feature {
 
  @Label("Feature 1") FEATURE_1,
 
  @Label("Feature 2") FEATURE_2;
 
  public boolean isActive() {
    return FeatureContext.getFeatureManager().isActive(this);
  }
 
}
```
 
Cria a seguinte classe que cria um bean para os Features e role do Spring Security (Não esquecer de implementar a Segurança):

```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.Feature;
import spartacus.captain.clearsale.togglz.SpartacusProjectNameTogglzFeatures;
 
@Configuration
public class ProjectNameTogglzConfig {
 
    private static final String ADMIN_AUTHORITY = "ADMIN";
 
    @Bean
    public Class<? extends Feature> getFeatureClass() {
        return SpartacusProjectNameTogglzFeatures.class;
    }
 
    @Bean
    public UserProvider getUserProvider() {
        return new SpringSecurityUserProvider(ADMIN_AUTHORITY);
    }
}
```

Adicionar a seguinte linha no default.properties do projeto, para definir o tempo de cache para as informações lidas do banco e o acesso ao banco:

```
spring.data.togglz.cacheTimeout:5
spring.data.mongodb.database: projectname
spring.data.mongodb.host: endereçodeorigem
```

Na classe de configuração do projeto deve ser importada, a seguinte classe: SharedTogglzConfig.class. Se tudo estiver ok, será possível acessar o console admin do togglz através de /tooglz/index.
 

