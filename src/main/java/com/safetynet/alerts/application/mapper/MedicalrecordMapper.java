package com.safetynet.alerts.application.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.safetynet.alerts.application.dto.MedicalrecordDTO;
import com.safetynet.alerts.application.dto.PersonInfoDTO;
import com.safetynet.alerts.application.repository.entity.Allergie;
import com.safetynet.alerts.application.repository.entity.MedicalRecord;
import com.safetynet.alerts.application.repository.entity.Medication;

/**
 * The Interface MedicalrecordMapper.
 */
@Mapper
public interface MedicalrecordMapper {

	/**
	 * Medical records to medicalrecord DT os.
	 *
	 * @param medicalRecords the medical records
	 * @return the list
	 */
	List<MedicalrecordDTO> medicalRecordsToMedicalrecordDTOs(List<MedicalRecord> medicalRecords);

	/**
	 * Medical record to medicalrecord DTO.
	 *
	 * @param medicalRecord the medical record
	 * @return the medicalrecord DTO
	 */
	MedicalrecordDTO medicalRecordToMedicalrecordDTO(MedicalRecord medicalRecord);

	/**
	 * MedicalrecordDTos to personn.
	 *
	 * @param medicalRecordDTO the medical record DTO
	 * @return the medical record
	 */
	@Mappings({
		@Mapping(source = "allergies", target = "allergies"),
		@Mapping(source = "medications", target = "medications"),
		@Mapping(target = "person", ignore = true)
	})	
	MedicalRecord medicalrecordDTOToMedicalRecord(MedicalrecordDTO medicalRecordDTO);

	/**
	 * From string to allergie.
	 *
	 * @param allergie the allergie
	 * @return the allergie
	 */
	default Allergie fromStringToAllergie(String allergie) {
		return Allergie.builder().name(allergie).build();
	}

	/**
	 * From allergie to string.
	 *
	 * @param allergie the allergie
	 * @return the string
	 */
	default String fromAllergieToString(Allergie allergie) {
		return allergie.getName();
	}

	/**
	 * From string to medication.
	 *
	 * @param medication the medication
	 * @return the medication
	 */
	default Medication fromStringToMedication(String medication) {
		return Medication.builder().name(medication).build();
	}

	/**
	 * From medication to string.
	 *
	 * @param medication the medication
	 * @return the string
	 */
	default String fromMedicationToString(Medication medication) {
		return medication.getName();
	}

	/**
	 * Update.
	 *
	 * @param medicalRecord the medical record
	 * @param medicalRecordDTO the medical record DTO
	 */
	void update(@MappingTarget MedicalRecord medicalRecord, MedicalrecordDTO medicalRecordDTO);

}
