import { ILanguage } from 'app/shared/model//language.model';

export interface IContact {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    language?: ILanguage;
}

export class Contact implements IContact {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public language?: ILanguage
    ) {}
}
