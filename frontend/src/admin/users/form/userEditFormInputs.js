import { formValidators } from "../../../validators/formValidators";

export const userEditFormInputs = [
  {
    tag: "Username",
    name: "username",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "First Name",
    name: "firstName",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "Last Name",
    name: "lastName",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "Studies",
    name: "studies",
    type: "text",
    defaultValue: "",
    isRequired: false,
    validators: [],
  },
  {
    tag: "Job",
    name: "job",
    type: "text",
    defaultValue: "",
    isRequired: false,
    validators: [],
  },
  {
    tag: "Email",
    name: "email",
    type: "text",
    defaultValue: "",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },
  {
    tag: "Authority",
    name: "authority",
    type: "select",
    values: ["USER","ADMIN"],
    defaultValue: "USER",
    isRequired: true,
    validators: [formValidators.notEmptyValidator],
  },

];