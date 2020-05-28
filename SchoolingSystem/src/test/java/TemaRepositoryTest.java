import domain.Tema;
import domain.validators.TemaValidator;
import domain.validators.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TemaRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TemaRepositoryTest {
    Tema t;
    TemaRepository temaRepository;

    @BeforeEach
    public void setUp() throws Exception{
        Validator<Tema> validatorTema = new TemaValidator();
        temaRepository = new TemaRepository(validatorTema);
        t = new Tema("1", "lab1", 1, 2);
        temaRepository.save(t);
    }

    @Test
    public void update(){
        assertThrows(IllegalArgumentException.class, ()->{
           temaRepository.update(null);
        });
    }
}
