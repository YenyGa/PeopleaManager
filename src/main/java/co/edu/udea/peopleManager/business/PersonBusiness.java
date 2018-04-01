package co.edu.udea.peopleManager.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udea.peopleManager.dao.PersonDao;
import co.edu.udea.peopleManager.model.Person;
import co.edu.udea.peopleManager.util.WebException;

import java.util.List;

@Service
public class PersonBusiness {

    private PersonDao personDao;

    @Autowired
    public PersonBusiness(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void checkDuplicateId(String id) throws WebException {
    	Person person = personDao.findById(id);

        if (person != null) {
            WebException webEx = new WebException();
            webEx.setUserMessage("La persona ya se encuentra registrada");
            webEx.setTechnicalMessage("la persona retornada en personDao.findById(id) no es nulo");
            throw webEx;
        }
    }
    
    public Person findById(String id) throws WebException{
        Person person = personDao.findById(id);

        if (person == null) {
            WebException webEx = new WebException();
            webEx.setUserMessage("El producto no se encuentra registrado");
            webEx.setTechnicalMessage("el producto retornado en productDao.findById(id) es nulo");
            throw webEx;
        }
        return person;
    }
    
    public List<Person> findAll(){
    	return personDao.findAll(false);
    }
    
    public void savePerson(Person person) {
        personDao.save(person);
    }
    
    public void deletePerson(String id){
    	personDao.deleteById(id);
    }
    
}
