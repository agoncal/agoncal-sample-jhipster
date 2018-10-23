import { Route } from '@angular/router';

import { AueDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: AueDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
