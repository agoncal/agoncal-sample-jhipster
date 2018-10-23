export interface IContact {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    languageName?: string;
    languageId?: number;
}

export class Contact implements IContact {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public languageName?: string,
        public languageId?: number
    ) {}
}
