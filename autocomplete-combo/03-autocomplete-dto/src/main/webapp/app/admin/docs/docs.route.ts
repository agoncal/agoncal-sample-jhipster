import { Route } from '@angular/router';

import { AudDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: AudDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
