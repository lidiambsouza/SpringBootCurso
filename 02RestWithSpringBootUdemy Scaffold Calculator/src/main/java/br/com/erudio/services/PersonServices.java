package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.converter.custom.PersonConverter;
import br.com.erudio.data.model.Person;
import br.com.erudio.data.vo.PersonVO;
import br.com.erudio.data.vo.PersonVOV2;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.PersonRepository;

@Service
public class PersonServices {

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonConverter converter;
		
	public PersonVO create(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		var entity = converter.convertVOToEntity(person);
		var vo = converter.convertEntityToVO(repository.save(entity));
		return vo;
	}
	
	public List<PersonVO> findAll() {
		
		return DozerConverter.parseListObject(repository.findAll(), PersonVO.class);
	}
	
	public PersonVO findById(Long id) {
	
		return DozerConverter.parseObject(repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID")) , PersonVO.class);
	}	
	
	public PersonVO update(PersonVO p) {
		Person entity = repository.findById(p.getId())
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID")) ;
		
		entity.setFirstName(p.getFirstName());
		entity.setLastName(p.getLastName());
		entity.setAddress(p.getAddress());
		entity.setGender(p.getGender());		
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		
		return vo;
	}
	
	public void delete(Long id) {
		Person entity =  repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID")) ;
		repository.delete(entity);
		
		
	}
	
	
	
}
